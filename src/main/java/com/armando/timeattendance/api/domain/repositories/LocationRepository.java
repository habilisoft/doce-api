package com.armando.timeattendance.api.domain.repositories;

import com.armando.timeattendance.api.domain.model.Location;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
public interface LocationRepository {
    Location save(Location location);

    Optional<Location> findByName(String name);
}
