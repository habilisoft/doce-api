package com.habilisoft.doce.api.web.data;

import com.habilisoft.doce.api.domain.model.Report;
import com.habilisoft.doce.api.domain.model.WorkShiftDay;
import com.habilisoft.doce.api.domain.model.punch.policy.PunchPolicyType;
import com.habilisoft.doce.api.persistence.entities.CompanyInfoEntity;
import com.habilisoft.doce.api.persistence.repositories.CompanyInfoJpaRepo;
import com.habilisoft.doce.api.serialization.BaseEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * Created on 11/11/22.
 */
@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataResource {
    private final CompanyInfoJpaRepo companyInfoJpaRepo;

    @GetMapping
    public ResponseEntity<?> getData() {
        CompanyInfoEntity companyInfo = companyInfoJpaRepo.findAll()
                .stream()
                .findFirst()
                .orElse(new CompanyInfoEntity());

        Data data = Data.builder()
                .days(WorkShiftDay.values())
                .reports(Report.values())
                .punchPolicyTypes(PunchPolicyType.values())
                .companyInfo(
                        Map.of("name", Optional.ofNullable(companyInfo.getName()).orElse("Compañia"))
                )
                .build();
        return ResponseEntity.ok(data);
    }

    @lombok.Data
    @Builder
    public static class Data {
        @JsonSerialize(contentConverter = BaseEnumConverter.class)
        WorkShiftDay[] days;
        @JsonSerialize(contentConverter = BaseEnumConverter.class)
        Report[] reports;
        @JsonSerialize(contentConverter = BaseEnumConverter.class)
        PunchPolicyType[] punchPolicyTypes;

        Map<String, String> companyInfo;
    }
}
