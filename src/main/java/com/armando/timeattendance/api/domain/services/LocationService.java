package com.armando.timeattendance.api.domain.services;

import com.armando.timeattendance.api.domain.commands.CreateLocation;
import com.armando.timeattendance.api.domain.model.Location;
import com.armando.timeattendance.api.domain.repositories.LocationRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 11/11/22.
 */
@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createLocation(CreateLocation request) {
        Location location = Location.builder()
                .name(request.name())
                .build();
        return locationRepository.save(location);
    }
}
