package com.habilisoft.doce.api.web.groups;

import com.habilisoft.doce.api.auth.base.utils.SortUtils;
import com.habilisoft.doce.api.domain.commands.CreateGroup;
import com.habilisoft.doce.api.domain.model.Group;
import com.habilisoft.doce.api.domain.services.GroupService;
import com.habilisoft.doce.api.persistence.converters.GroupJpaConverter;
import com.habilisoft.doce.api.persistence.entities.EmployeeEntity;
import com.habilisoft.doce.api.persistence.entities.GroupEntity;
import com.habilisoft.doce.api.persistence.repositories.GroupJpaRepo;
import com.habilisoft.doce.api.persistence.services.GroupSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created on 11/11/22.
 */
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupResource {
    private final GroupService groupService;
    private final GroupSearchService groupSearchService;
    private final GroupJpaConverter converter;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateGroup request) {
        Group group = groupService.createGroup(request);
        return ResponseEntity.ok(group);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<?> update(@PathVariable Long groupId, @RequestBody CreateGroup request) {
        groupService.updateGrop(groupId,request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<?> searchEmployees(@RequestParam final Map<String, Object> queryMap,
                                   @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                                   @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
                                   @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {

        if (!StringUtils.hasLength(sort))
            sort = "-" + "name";

        Page<GroupEntity> entityPage =  groupSearchService.search(
                queryMap,
                PageRequest.of(
                        page,
                        size,
                        SortUtils.processSort(sort, new String[]{"name"})
                )
        );

        return entityPage.map(converter::fromJpaEntity);
    }
}
