package com.habilisoft.doce.api.persistence.entities;

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

/**
 * Created on 14/1/23.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee_device_data")
public class EmployeeDeviceDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_device_data_gen")
    @SequenceGenerator(name = "employee_device_data_gen", sequenceName = "employee_device_data_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    private EmployeeEntity employee;
    @Column
    private Integer number;
    @Column
    private String deviceModel;
    @Column(columnDefinition = "TEXT")
    private String record;
}
