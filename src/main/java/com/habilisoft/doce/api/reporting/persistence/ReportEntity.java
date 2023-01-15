package com.habilisoft.doce.api.reporting.persistence;

import com.habilisoft.doce.api.reporting.domain.FilterField;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 6/1/23.
 */
@Data
@Entity
@Table(name = "reports")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reports_gen")
    @SequenceGenerator(name = "reports_gen", sequenceName = "reports_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(columnDefinition = "TEXT")
    private String query;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<FilterField> filterFields;

}
