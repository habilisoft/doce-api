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

/**
 * Created on 2019-04-27.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "groups")
public class GroupEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "groups_id_seq", sequenceName = "groups_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;
}
