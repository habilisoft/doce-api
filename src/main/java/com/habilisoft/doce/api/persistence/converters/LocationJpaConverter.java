package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.Location;
import com.habilisoft.doce.api.persistence.entities.LocationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
@Component
public class LocationJpaConverter implements JpaConverter<Location, LocationEntity> {
    private final ModelMapper modelMapper;

    public LocationJpaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Location fromJpaEntity(LocationEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map(j -> modelMapper.map(j, Location.class))
                .orElse(null);
    }

    @Override
    public LocationEntity toJpaEntity(Location domainObject) {
        return Optional.ofNullable(domainObject)
                .map(d -> modelMapper.map(d, LocationEntity.class))
                .orElse(null);
    }
}
