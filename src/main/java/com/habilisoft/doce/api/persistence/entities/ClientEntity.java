package com.habilisoft.doce.api.persistence.entities;

import com.habilisoft.doce.api.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

@Data
@Entity
@Table(schema = "public", name = "clients")
public class ClientEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "clients_id_seq", sequenceName = "clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sub_domain_name", unique = true, updatable = false)
    private String subDomainName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "employees_count")
    private Long employeesCount;

    @JoinColumn(name = "plan_id")
    @OneToOne(fetch = FetchType.LAZY)
    private PlanEntity plan;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClientDeviceEntity> clientDeviceEntities;

    public void setClientDeviceEntities(Set<ClientDeviceEntity> clientDeviceEntities) {
        if (clientDeviceEntities != null)
            clientDeviceEntities.forEach(d -> d.setClient(this));

        this.clientDeviceEntities = clientDeviceEntities;
    }
}
