package com.armando.timeattendance.api.persistence.repositories;

import com.armando.timeattendance.api.auth.base.repositories.ExtendedRepository;
import com.armando.timeattendance.api.domain.model.LocationType;
import com.armando.timeattendance.api.persistence.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created on 2019-04-22.
 */
@Repository
public interface EmployeeJpaRepo extends ExtendedRepository<EmployeeEntity, Long> {

    @Query("SELECT coalesce(MAX(e.enrollId)+1,1) from EmployeeEntity e ")
    Integer getNextEnrollId();

    Optional<EmployeeEntity> findByEnrollId(Integer enrollId);

    List<EmployeeEntity> findByLocationType(LocationType locationType);

    @Modifying
    @Query(value = "UPDATE employees SET work_shift_id = :newWorkShift WHERE work_shift_id = :oldWorkShift", nativeQuery = true)
    void updateEmployeesWorkShift(@Param("oldWorkShift") Long oldWorkShift, @Param("newWorkShift") Long newWorkShift);

    Optional<EmployeeEntity> findByDocumentNumber(@Param("documentNumber") String documentNumber);
}
