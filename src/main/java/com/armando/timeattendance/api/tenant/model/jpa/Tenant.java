package com.armando.timeattendance.api.tenant.model.jpa;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Daniel
 */
@Data
@Entity
@Table(catalog = "public", schema = "public")
public class Tenant implements Serializable {

    @Id
    private String name;
    @CreationTimestamp
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;
}
