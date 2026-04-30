package com.habilisoft.doce.api.web.dashboard;

import com.habilisoft.doce.api.persistence.entities.CompanyInfoEntity;
import com.habilisoft.doce.api.persistence.entities.UserEntity;
import com.habilisoft.doce.api.persistence.mapping.TimeAttendanceSummaryReport;
import com.habilisoft.doce.api.persistence.repositories.CompanyInfoJpaRepo;
import com.habilisoft.doce.api.persistence.repositories.DeviceJpaRepo;
import com.habilisoft.doce.api.persistence.repositories.EmployeeJpaRepo;
import com.habilisoft.doce.api.persistence.repositories.GroupJpaRepo;
import com.habilisoft.doce.api.persistence.repositories.TimeAttendanceRecordJpaRepository;
import com.habilisoft.doce.api.persistence.repositories.WorkShiftJpaRepo;
import com.habilisoft.doce.api.persistence.users.UserJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardResource {
    private static final String HIDDEN_USERNAME = "superadmin";

    private final CompanyInfoJpaRepo companyInfoJpaRepo;
    private final EmployeeJpaRepo employeeJpaRepo;
    private final GroupJpaRepo groupJpaRepo;
    private final WorkShiftJpaRepo workShiftJpaRepo;
    private final DeviceJpaRepo deviceJpaRepo;
    private final UserJpaRepository userJpaRepository;
    private final TimeAttendanceRecordJpaRepository timeAttendanceRecordJpaRepository;

    @GetMapping
    public ResponseEntity<DashboardSummary> getDashboard() {
        CompanyInfoEntity companyInfo = companyInfoJpaRepo.findAll()
                .stream()
                .findFirst()
                .orElse(new CompanyInfoEntity());

        long totalEmployees = employeeJpaRepo.count();
        long activeEmployees = employeeJpaRepo.countByActiveTrue();
        long totalDevices = deviceJpaRepo.count();
        long connectedDevices = deviceJpaRepo.countByConnectedTrue();
        long totalUsers = userJpaRepository.countByUsernameNotIgnoreCase(HIDDEN_USERNAME);
        long usersWithLastLogin = userJpaRepository.countByUsernameNotIgnoreCaseAndLastLoginIsNotNull(HIDDEN_USERNAME);
        AttendanceToday attendanceToday = getTodayAttendance();

        DashboardSummary summary = DashboardSummary.builder()
                .companyName(Optional.ofNullable(companyInfo.getName()).orElse("Compañia"))
                .employees(CountSummary.builder()
                        .total(totalEmployees)
                        .active(activeEmployees)
                        .inactive(Math.max(totalEmployees - activeEmployees, 0))
                        .build())
                .devices(CountSummary.builder()
                        .total(totalDevices)
                        .active(connectedDevices)
                        .inactive(Math.max(totalDevices - connectedDevices, 0))
                        .build())
                .users(CountSummary.builder()
                        .total(totalUsers)
                        .active(usersWithLastLogin)
                        .inactive(Math.max(totalUsers - usersWithLastLogin, 0))
                        .build())
                .groups(groupJpaRepo.count())
                .workShifts(workShiftJpaRepo.count())
                .attendanceToday(attendanceToday)
                .recentLogins(userJpaRepository
                        .findTop5ByUsernameNotIgnoreCaseAndLastLoginIsNotNullOrderByLastLoginDesc(HIDDEN_USERNAME)
                        .stream()
                        .map(this::toRecentLogin)
                        .collect(Collectors.toList()))
                .build();

        return ResponseEntity.ok(summary);
    }

    private AttendanceToday getTodayAttendance() {
        Date today = new Date();
        TimeAttendanceSummaryReport summary = timeAttendanceRecordJpaRepository.findTimeAttendanceSummary(today, today)
                .stream()
                .findFirst()
                .orElse(new TimeAttendanceSummaryReport(today, 0L, 0L, 0L, 0L));

        return AttendanceToday.builder()
                .onTime(Optional.ofNullable(summary.getOnTime()).orElse(0L))
                .late(Optional.ofNullable(summary.getLate()).orElse(0L))
                .pendingPunch(Optional.ofNullable(summary.getNoPunch()).orElse(0L))
                .build();
    }

    private RecentLogin toRecentLogin(UserEntity user) {
        return RecentLogin.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .lastLogin(user.getLastLogin())
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class DashboardSummary {
        private String companyName;
        private CountSummary employees;
        private CountSummary devices;
        private CountSummary users;
        private long groups;
        private long workShifts;
        private AttendanceToday attendanceToday;
        private List<RecentLogin> recentLogins;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class CountSummary {
        private long total;
        private long active;
        private long inactive;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class AttendanceToday {
        private long onTime;
        private long late;
        private long pendingPunch;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class RecentLogin {
        private Long id;
        private String name;
        private String username;
        private java.util.Date lastLogin;
    }
}
