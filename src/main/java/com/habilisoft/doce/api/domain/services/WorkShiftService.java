package com.habilisoft.doce.api.domain.services;

import com.habilisoft.doce.api.domain.exceptions.WorkShiftWithoutDetailsException;
import com.habilisoft.doce.api.domain.model.WorkShift;
import com.habilisoft.doce.api.domain.model.WorkShiftDetail;
import com.habilisoft.doce.api.domain.repositories.WorkShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 2019-04-27.
 */
@Service
@RequiredArgsConstructor
public class WorkShiftService {
    private final WorkShiftRepository repository;

    public WorkShift save(WorkShift workShift) {
        workShift.setDetails(
                workShift.getDetails().stream()
                        .filter(WorkShiftDetail::getSelected)
                        .collect(Collectors.toSet()));
        workShift.setWeekWorkHours(this.getWeekWorkHours(workShift));
        return repository.save(workShift);
    }

    @Transactional
    public WorkShift update(Long workShiftId, WorkShift workShift) {
        workShift.setDetails(
                workShift.getDetails()
                        .stream()
                        .filter(WorkShiftDetail::getSelected)
                        .collect(Collectors.toSet()));
        workShift.setWeekWorkHours(getWeekWorkHours(workShift));

        return repository.save(workShift);
    }

    public Float getWeekWorkHours(final WorkShift workShift) {
        final Set<WorkShiftDetail> details = workShift.getDetails();
        return Optional.ofNullable(details)
                        .map(det -> det
                                .stream()
                                .filter(WorkShiftDetail::getSelected)
                                .map(d -> {
                                    long totalTime = d.getStartTime().until(d.getEndTime(), ChronoUnit.SECONDS);
                                    long breakTime = Optional.of(workShift.getPunchForBreak())
                                            .filter(v -> v)
                                            .map((v) -> d.getBreakStartTime().until(d.getBreakEndTime(), ChronoUnit.SECONDS))
                                            .orElseGet(() -> workShift.getBreakMinutes() * 60L);
                                    return  (totalTime - breakTime) / 3600f;

                                })
                                .reduce(Float::sum)
                                .orElse(0f))
                .orElseThrow(WorkShiftWithoutDetailsException::new);
    }
}
