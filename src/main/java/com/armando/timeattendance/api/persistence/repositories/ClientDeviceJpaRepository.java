package com.armando.timeattendance.api.persistence.repositories;

import com.armando.timeattendance.api.persistence.entities.ClientDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientDeviceJpaRepository extends JpaRepository<ClientDeviceEntity, Long> {
    Optional<ClientDeviceEntity> findBySerialNumber(String serialNumber);

    @Query(nativeQuery = true, value = "select c.sub_domain_name from client_devices d INNER JOIN clients c on  c.id = d.client_id where d.serial_number = :sn")
    String getClientSubDomainByDeviceSerialNumber(@Param("sn") String serialNumber);
}
