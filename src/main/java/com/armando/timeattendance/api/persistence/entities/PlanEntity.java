package com.armando.timeattendance.api.persistence.entities;


import com.armando.timeattendance.api.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(schema = "public", name = "plans")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "plans_id_seq", sequenceName = "plans_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plans_id_seq")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

}
