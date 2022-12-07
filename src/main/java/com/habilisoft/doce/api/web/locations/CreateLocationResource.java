package com.habilisoft.doce.api.web.locations;

import com.habilisoft.doce.api.domain.commands.CreateLocation;
import com.habilisoft.doce.api.domain.model.Location;
import com.habilisoft.doce.api.domain.services.LocationService;
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
