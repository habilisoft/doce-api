package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.domain.repositories.DeviceLanguagesRepository;
import com.habilisoft.doce.api.dto.device.DeviceLanguage;
import com.habilisoft.doce.api.persistence.entities.DeviceLanguageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Created on 26/2/24.
 */
@Repository
@RequiredArgsConstructor
public class DefaultDeviceLanguageRepository implements DeviceLanguagesRepository {
    private final DeviceLanguagesJpaRepo jpaRepo;

    @Override
    public Integer getLanguageValue(String deviceModel, DeviceLanguage language) {
        return jpaRepo.findById(
                        DeviceLanguageEntity.Pk.builder()
                                .deviceModel(deviceModel)
                                .language(language)
                                .build()
                )
                .map(DeviceLanguageEntity::getValue)
                .orElse(language.value());
    }
}
