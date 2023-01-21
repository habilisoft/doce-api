package com.habilisoft.doce.api.reporting.persistence.entities;

import com.habilisoft.doce.api.reporting.domain.model.ReportQueryFilter;
import com.habilisoft.doce.api.reporting.domain.model.ReportUIColumn;
import com.habilisoft.doce.api.reporting.domain.model.ReportUIFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

/**
 * Created on 6/1/23.
 */
@Data
@Entity
@Table(name = "reports")
@NoArgsConstructor
@AllArgsConstructor
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reports_gen")
    @SequenceGenerator(name = "reports_gen", sequenceName = "reports_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String slug;

    @Column
    private String description;

    @Column(columnDefinition = "TEXT")
    private String query;

    @Column(columnDefinition = "TEXT")
    private String countQuery;

    @Column
    private String defaultOrder;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<ReportUIFilter> uiFilters;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<ReportUIColumn> uiColumns;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<ReportQueryFilter> queryFilters;

    public ReportEntity(String query, String countQuery) {
        this.query = query;
        this.countQuery = countQuery;
    }

    public ReportEntity(String query, String countQuery, String defaultOrder) {
        this.query = query;
        this.countQuery = countQuery;
        this.defaultOrder = defaultOrder;
    }

    public ReportEntity(String query, String countQuery, String defaultOrder, Object queryFilters) {
        this.query = query;
        this.countQuery = countQuery;
        this.defaultOrder = defaultOrder;
        this.queryFilters = (List<ReportQueryFilter>) queryFilters;
    }
}
