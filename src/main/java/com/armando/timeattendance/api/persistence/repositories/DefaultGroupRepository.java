package com.armando.timeattendance.api.persistence.repositories;

import com.armando.timeattendance.api.domain.model.Group;
import com.armando.timeattendance.api.domain.repositories.GroupRepository;
import com.armando.timeattendance.api.persistence.converters.GroupJpaConverter;
import com.armando.timeattendance.api.persistence.entities.GroupEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
@Repository
@RequiredArgsConstructor
public class DefaultGroupRepository implements GroupRepository {
    private final GroupJpaRepo jpaRepo;
    private final GroupJpaConverter converter;

    @Override
    public Group save(Group group) {
        GroupEntity entity = converter.toJpaEntity(group);
        jpaRepo.save(entity);
        return converter.fromJpaEntity(entity);
    }

    @Override
    public Optional<Group> findByName(String name) {
        return jpaRepo.findByNameIgnoreCase(name)
                .map(converter::fromJpaEntity);
    }
}
