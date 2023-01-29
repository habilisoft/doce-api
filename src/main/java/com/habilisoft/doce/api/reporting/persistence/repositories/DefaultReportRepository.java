package com.habilisoft.doce.api.reporting.persistence.repositories;

import com.habilisoft.doce.api.reporting.domain.model.Report;
import com.habilisoft.doce.api.reporting.domain.repositories.ReportRepository;
import com.habilisoft.doce.api.reporting.persistence.converters.ReportJpaConverter;
import com.habilisoft.doce.api.reporting.persistence.entities.ReportEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created on 15/1/23.
 */
@Repository
@RequiredArgsConstructor
public class DefaultReportRepository implements ReportRepository {
    private final ReportJpaRepo jpaRepo;
    private final ReportJpaConverter converter;

    @Override
    public Report save(Report report) {
        ReportEntity entity = converter.toJpaEntity(report);
        jpaRepo.save(entity);
        return converter.fromJpaEntity(entity);
    }

    @Override
    public List<Report> list() {
        return jpaRepo.findAll()
                .stream()
                .map(converter::fromJpaEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Report getQueryBySlug(String reportSlug) {
        ReportEntity entity = jpaRepo.getQueryBySlug(reportSlug);
        return converter.fromJpaEntity(entity);
    }

    @Override
    public Optional<Report> findBySlug(String reportSlug) {
        return jpaRepo.findBySlug(reportSlug)
                .map(converter::fromJpaEntity);
    }

    @Override
    public Optional<Report> findById(Long id) {
        return jpaRepo.findById(id)
                .map(converter::fromJpaEntity);
    }
}
