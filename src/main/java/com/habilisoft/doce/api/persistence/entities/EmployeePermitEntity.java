package com.habilisoft.doce.api.persistence.entities;

import com.habilisoft.doce.api.domain.model.PermitType;
import com.habilisoft.doce.api.persistence.BaseEntity;
import com.habilisoft.doce.api.persistence.entities.BaseEmployee;
import com.habilisoft.doce.api.persistence.entities.PermitTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.util.Date;

/**
 * Created on 19/2/23.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee_permits")
public class EmployeePermitEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_permit_gen")
    @SequenceGenerator(name = "employee_permit_gen", sequenceName = "employee_permit_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private BaseEmployee employee;

    @Column(name = "from_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date toDate;

    @OneToOne
    private PermitTypeEntity permitType;

    @Column(name = "comment")
    private String comment;
}
