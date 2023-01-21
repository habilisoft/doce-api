package com.habilisoft.doce.api.reporting.persistence.converters;

import com.habilisoft.doce.api.persistence.converters.JpaConverter;
import com.habilisoft.doce.api.reporting.domain.model.Report;
import com.habilisoft.doce.api.reporting.persistence.entities.ReportEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 15/1/23.
 */
@Component
@RequiredArgsConstructor
public class ReportJpaConverter implements JpaConverter<Report, ReportEntity> {
    private final ModelMapper modelMapper;

    @Override
    public Report fromJpaEntity(ReportEntity j) {
        return Optional.ofNullable(j)
                .map(j1 -> modelMapper.map(j1, Report.class))
                .orElse(null);
    }

    @Override
    public ReportEntity toJpaEntity(Report d) {
        return Optional.ofNullable(d)
                .map(d1 -> modelMapper.map(d1, ReportEntity.class))
                .orElse(null);
    }
}
