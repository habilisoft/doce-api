package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.persistence.entities.DeviceLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 26/2/24.
 */
public interface DeviceLanguagesJpaRepo extends JpaRepository<DeviceLanguageEntity, DeviceLanguageEntity.Pk> {
}
