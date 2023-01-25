package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.auth.base.repositories.ExtendedRepository;
import com.habilisoft.doce.api.domain.model.WorkShift;
import com.habilisoft.doce.api.persistence.entities.WorkShiftEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created on 2019-04-27.
 */
@Repository
public interface WorkShiftJpaRepo extends ExtendedRepository<WorkShiftEntity, Long> {
    Optional<WorkShiftEntity> findByNameIgnoreCase(String name);

    @Query("SELECT new WorkShiftEntity(w.id, w.name) FROM WorkShiftEntity  w WHERE w.name like %:name%")
    Page<WorkShiftEntity> searchByName(@Param("name") String name, Pageable pageable);
}
