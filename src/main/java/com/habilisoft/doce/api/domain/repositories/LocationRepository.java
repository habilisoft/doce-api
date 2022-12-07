package com.habilisoft.doce.api.domain.repositories;

import com.habilisoft.doce.api.domain.model.Location;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
public interface LocationRepository {
    Location save(Location location);

    Optional<Location> findByName(String name);
}
