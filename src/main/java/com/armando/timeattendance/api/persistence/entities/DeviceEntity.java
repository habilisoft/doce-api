package com.armando.timeattendance.api.persistence.entities;

import com.armando.timeattendance.api.persistence.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created on 2019-04-21.
 */
@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "devices")
@NoArgsConstructor
public class DeviceEntity extends BaseEntity {
    @Id
    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "description")
    private String description;
    @Embedded
    private DeviceInfoEmbeddable deviceInfo;

    public DeviceEntity(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "connected")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean connected;

    @Override
    public Long getId() {
        return null;
    }
}
