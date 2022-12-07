package com.habilisoft.doce.api.web.locations;

import com.habilisoft.doce.api.domain.commands.CreateGroup;
import com.habilisoft.doce.api.domain.model.Group;
import com.habilisoft.doce.api.domain.services.GroupService;
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
