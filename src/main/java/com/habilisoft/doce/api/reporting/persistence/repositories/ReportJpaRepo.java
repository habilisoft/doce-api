package com.habilisoft.doce.api.reporting.persistence.repositories;

import com.habilisoft.doce.api.reporting.persistence.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created on 15/1/23.
 */
public interface ReportJpaRepo extends JpaRepository<ReportEntity, Long> {
    @Query("SELECT new ReportEntity(r.query, r.countQuery, r.defaultOrder, r.queryFilters) FROM ReportEntity r WHERE  r.slug = :slug")
    ReportEntity getQueryBySlug(@Param("slug") String slug);

    Optional<ReportEntity> findBySlug(String slug);
}
