package com.habilisoft.doce.api.persistence.entities;

import com.habilisoft.doce.api.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created on 19/2/23.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Subselect("SELECT\n" +
        "    id,\n" +
        "    full_name,\n" +
        "    enroll_id,\n" +
        "    NULL AS created_by,\n" +
        "    NULL AS created_date,\n" +
        "    NULL AS last_modified_by,\n" +
        "    NULL AS last_modified_date,\n" +
        "    NULL as deleted " +
        "FROM\n" +
        "    employees")
public class BaseEmployee extends BaseEntity {
    @Id
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "enroll_id")
    private Integer enrollId;
}
