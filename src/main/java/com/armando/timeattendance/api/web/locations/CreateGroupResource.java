package com.armando.timeattendance.api.web.locations;

import com.armando.timeattendance.api.domain.commands.CreateGroup;
import com.armando.timeattendance.api.domain.model.Group;
import com.armando.timeattendance.api.domain.model.Location;
import com.armando.timeattendance.api.domain.services.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 11/11/22.
 */
@RestController
public class CreateGroupResource {
    private final GroupService groupService;

    public CreateGroupResource(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/groups")
    public ResponseEntity<?> createLocation(@RequestBody CreateGroup request) {
        Group group = groupService.createGroup(request);
        return ResponseEntity.ok(group);
    }
}
