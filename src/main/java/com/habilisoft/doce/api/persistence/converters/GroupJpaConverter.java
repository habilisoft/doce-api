package com.habilisoft.doce.api.persistence.converters;

import com.habilisoft.doce.api.domain.model.Group;
import com.habilisoft.doce.api.persistence.entities.GroupEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created on 11/11/22.
 */
@Component
public class GroupJpaConverter implements JpaConverter<Group, GroupEntity> {
    private final ModelMapper modelMapper;

    public GroupJpaConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Group fromJpaEntity(GroupEntity jpaEntity) {
        return Optional.ofNullable(jpaEntity)
                .map((e) -> modelMapper.map(e, Group.class))
                .orElse(null);
    }

    @Override
    public GroupEntity toJpaEntity(Group domainObject) {
        return Optional.ofNullable(domainObject)
                .map((e) -> modelMapper.map(e, GroupEntity.class))
                .orElse(null);
    }
}
