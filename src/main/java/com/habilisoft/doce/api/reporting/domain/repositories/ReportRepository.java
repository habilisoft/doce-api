package com.habilisoft.doce.api.reporting.domain.repositories;

import com.habilisoft.doce.api.reporting.domain.model.Report;

import java.util.List;
import java.util.Optional;

/**
 * Created on 15/1/23.
 */
public interface ReportRepository {
    Report save(Report report);
    List<Report> list();
    Report getQueryBySlug(String reportSlug);
    Optional<Report> findBySlug(String reportSlug);
    Optional<Report> findById(Long id);
}
