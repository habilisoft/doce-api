package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.Device;
import com.habilisoft.doce.api.dto.device.DeviceInfo;
import com.habilisoft.doce.api.persistence.entities.DeviceEntity;
import com.habilisoft.doce.api.persistence.entities.DeviceInfoEmbeddable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Created on 11/11/22.
 */
@Component
public class DeviceJpaConverter implements JpaConverter<Device, DeviceEntity>{
    private final ModelMapper  modelMapper;
    private final LocationJpaConverter locationJpaConverter;

    public DeviceJpaConverter(final ModelMapper modelMapper,
                              final LocationJpaConverter locationJpaConverter) {
        this.modelMapper = modelMapper;
        this.locationJpaConverter = locationJpaConverter;
    }

    @Override
    public Device fromJpaEntity(DeviceEntity jpaEntity) {
        return Device.builder()
                .active(jpaEntity.getActive())
                .connected(jpaEntity.getConnected())
                .description(jpaEntity.getDescription())
                .deviceInfo(modelMapper.map(jpaEntity.getDeviceInfo(), DeviceInfo.class))
                .location(locationJpaConverter.fromJpaEntity(jpaEntity.getLocation()))
                .serialNumber(jpaEntity.getSerialNumber())
                .sessionId(jpaEntity.getSessionId())
                .build();
    }

    @Override
    public DeviceEntity toJpaEntity(Device device) {
        return DeviceEntity.builder()
                .active(device.getActive())
                .connected(device.getConnected())
                .description(device.getDescription())
                .deviceInfo(modelMapper.map(device.getDeviceInfo(), DeviceInfoEmbeddable.class))
                .location(locationJpaConverter.toJpaEntity(device.getLocation()))
                .serialNumber(device.getSerialNumber())
                .sessionId(device.getSessionId())
                .build();
    }
}
