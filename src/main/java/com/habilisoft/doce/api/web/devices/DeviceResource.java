package com.habilisoft.doce.api.web.devices;

import com.habilisoft.doce.api.auth.base.utils.SortUtils;
import com.habilisoft.doce.api.persistence.converters.DeviceJpaConverter;
import com.habilisoft.doce.api.persistence.entities.DeviceEntity;
import com.habilisoft.doce.api.persistence.services.DeviceSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created on 8/12/22.
 */
@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceResource {
    private final DeviceSearchService deviceSearchService;
    private final DeviceJpaConverter converter;

    @GetMapping
    public Page<?> search(@RequestParam final Map<String, Object> queryMap,
                          @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                          @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
                          @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {

        if (!StringUtils.hasLength(sort))
            sort = "-" + "createdDate";

        Page<DeviceEntity> entityPage = deviceSearchService.search(
                queryMap,
                PageRequest.of(
                        page,
                        size,
                        SortUtils.processSort(sort, new String[]{"fullName"})
                )
        );

        return entityPage.map(converter::fromJpaEntity);
    }
}
