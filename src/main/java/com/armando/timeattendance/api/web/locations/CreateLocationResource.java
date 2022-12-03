package com.armando.timeattendance.api.web.locations;

import com.armando.timeattendance.api.domain.commands.CreateLocation;
import com.armando.timeattendance.api.domain.model.Location;
import com.armando.timeattendance.api.domain.services.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 11/11/22.
 */
@RestController
public class CreateLocationResource {
    private final LocationService locationService;

    public CreateLocationResource(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/locations")
    public ResponseEntity<?> createLocation(@RequestBody CreateLocation request) {
        Location location = locationService.createLocation(request);
        return ResponseEntity.ok(location);
    }
}
