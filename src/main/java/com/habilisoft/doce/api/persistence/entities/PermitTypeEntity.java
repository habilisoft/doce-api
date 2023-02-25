package com.habilisoft.doce.api.persistence.entities;

import com.habilisoft.doce.api.persistence.BaseEntity;
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

/**
 * Created on 18/2/23.
 */
@Builder
@Entity
@Table(name = "permit_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermitTypeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_type_gen")
    @SequenceGenerator(name = "permit_type_gen", sequenceName = "permit_type_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String description;
}
