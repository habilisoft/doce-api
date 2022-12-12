package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.domain.model.Group;
import com.habilisoft.doce.api.domain.repositories.GroupRepository;
import com.habilisoft.doce.api.persistence.converters.GroupJpaConverter;
import com.habilisoft.doce.api.persistence.entities.GroupEntity;
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

    @Override
    public Optional<Group> findById(Long id) {
        return jpaRepo.findById(id)
                .map(converter::fromJpaEntity);
    }
}
