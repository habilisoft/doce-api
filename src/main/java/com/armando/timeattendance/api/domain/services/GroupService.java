package com.armando.timeattendance.api.domain.services;

import com.armando.timeattendance.api.domain.commands.CreateGroup;
import com.armando.timeattendance.api.domain.model.Group;
import com.armando.timeattendance.api.domain.repositories.GroupRepository;
import org.springframework.stereotype.Service;

/**
 * Created on 11/11/22.
 */
@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group createGroup(CreateGroup request) {
        return groupRepository.save(
                Group.builder()
                        .name(request.name())
                        .build()
        );
    }
}
