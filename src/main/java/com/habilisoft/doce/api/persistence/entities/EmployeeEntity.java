package com.habilisoft.doce.api.persistence.entities;

import com.habilisoft.doce.api.domain.model.LocationType;
import com.habilisoft.doce.api.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created on 2019-04-21.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "update employees set deleted = true where id = ?")
@Builder
public class EmployeeEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "employee_id_seq", sequenceName = "employee_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_seq")
    private Long id;
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "enroll_id", unique = true)
    private Integer enrollId;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "document_number")
    private String documentNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @Column(name = "fp_data", columnDefinition = "TEXT")
    private String fingerprintData;

    @Column(name = "location_type")
    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "work_shift_id")
    private WorkShiftEntity workShift;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private GroupEntity group;
}
