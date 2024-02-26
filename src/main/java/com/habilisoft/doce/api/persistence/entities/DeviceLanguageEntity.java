package com.habilisoft.doce.api.persistence.entities;

import com.habilisoft.doce.api.dto.device.DeviceLanguage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created on 9/9/23.
 */
@Entity
@Getter
@Setter
@Table(name = "device_languages", schema = "public")
public class DeviceLanguageEntity {
    @EmbeddedId
    private Pk id;
    private Integer value;

    @Getter
    @Setter
    @Embeddable
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pk implements Serializable {
        private String deviceModel;
        @Column
        @Enumerated(EnumType.STRING)
        private DeviceLanguage language;
    }
}
