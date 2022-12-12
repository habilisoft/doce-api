package com.habilisoft.doce.api.persistence.services;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.auth.base.services.BaseService;
import com.habilisoft.doce.api.persistence.entities.GroupEntity;
import com.habilisoft.doce.api.persistence.repositories.GroupJpaRepo;
import org.springframework.stereotype.Service;

/**
 * Created on 11/12/22.
 */
@Service
public class GroupSearchService extends BaseService<GroupEntity, Long> {
    private final GroupJpaRepo jpaRepo;
    public GroupSearchService(GroupJpaRepo jpaRepo) {
        super(GroupEntity.class);
        this.jpaRepo = jpaRepo;
    }

    @Override
    public ExtendedRepository<GroupEntity, Long> getRepository() {
        return jpaRepo;
    }
}
