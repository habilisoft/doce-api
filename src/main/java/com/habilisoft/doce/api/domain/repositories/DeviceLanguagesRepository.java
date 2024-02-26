package com.habilisoft.doce.api.domain.repositories;

import com.habilisoft.doce.api.dto.device.DeviceLanguage;

/**
 * Created on 26/2/24.
 */
public interface DeviceLanguagesRepository {
    Integer getLanguageValue(String deviceModel, DeviceLanguage language);
}
