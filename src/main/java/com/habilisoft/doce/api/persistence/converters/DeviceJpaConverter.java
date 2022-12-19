package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.Device;
import com.habilisoft.doce.api.dto.device.DeviceInfo;
import com.habilisoft.doce.api.persistence.entities.DeviceEntity;
import com.habilisoft.doce.api.persistence.entities.DeviceInfoEmbeddable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
@Component
public class DeviceJpaConverter implements JpaConverter<Device, DeviceEntity> {
    private final ModelMapper modelMapper;
    private final LocationJpaConverter locationJpaConverter;

    public DeviceJpaConverter(final ModelMapper modelMapper,
                              final LocationJpaConverter locationJpaConverter) {
        this.modelMapper = modelMapper;
        this.locationJpaConverter = locationJpaConverter;
    }

    @Override
    public Device fromJpaEntity(DeviceEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        return Device.builder()
                .active(jpaEntity.getActive())
                .connected(jpaEntity.getConnected())
                .description(jpaEntity.getDescription())
                .deviceInfo(
                        Optional.ofNullable(jpaEntity.getDeviceInfo()).map(d -> modelMapper.map(d, DeviceInfo.class))
                                .orElse(null)
                )
                .location(locationJpaConverter.fromJpaEntity(jpaEntity.getLocation()))
                .serialNumber(jpaEntity.getSerialNumber())
                .sessionId(jpaEntity.getSessionId())
                .build();
    }

    @Override
    public DeviceEntity toJpaEntity(Device device) {
        if (device == null) {
            return null;
        }
        return DeviceEntity.builder()
                .active(device.getActive())
                .connected(device.getConnected())
                .description(device.getDescription())
                .deviceInfo(
                        Optional.ofNullable(device.getDeviceInfo())
                                .map(d -> modelMapper.map(device.getDeviceInfo(), DeviceInfoEmbeddable.class))
                                .orElse(null)
                )
                .location(locationJpaConverter.toJpaEntity(device.getLocation()))
                .serialNumber(device.getSerialNumber())
                .sessionId(device.getSessionId())
                .build();
    }
}
