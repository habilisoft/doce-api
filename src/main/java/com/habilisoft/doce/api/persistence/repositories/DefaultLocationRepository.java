package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.domain.model.Location;
import com.habilisoft.doce.api.domain.repositories.LocationRepository;
import com.habilisoft.doce.api.persistence.converters.LocationJpaConverter;
import com.habilisoft.doce.api.persistence.entities.LocationEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
@Repository
public class DefaultLocationRepository implements LocationRepository {
    private final LocationJpaRepo jpaRepo;
    private final LocationJpaConverter converter;

    public DefaultLocationRepository(final LocationJpaRepo jpaRepo,
                                     final LocationJpaConverter converter) {
        this.jpaRepo = jpaRepo;
        this.converter = converter;
    }

    @Override
    public Location save(Location location) {
        LocationEntity entity = jpaRepo.save(converter.toJpaEntity(location));

        return converter.fromJpaEntity(entity);
    }

    @Override
    public Optional<Location> findByName(String name) {
        return jpaRepo.findByNameIgnoreCase(name)
                .map(converter::fromJpaEntity);
    }
}
