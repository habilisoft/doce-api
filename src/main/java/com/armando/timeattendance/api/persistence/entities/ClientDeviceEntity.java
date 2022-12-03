package com.armando.timeattendance.api.persistence.entities;

import com.armando.timeattendance.api.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(schema = "public", name = "client_devices")
public class ClientDeviceEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "client_devices_id_seq", sequenceName = "client_devices_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_devices_id_seq")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @Column(name = "serial_number", unique = true)
    private String serialNumber;
}
