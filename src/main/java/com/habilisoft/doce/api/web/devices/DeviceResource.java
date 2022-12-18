package com.habilisoft.doce.api.web.devices;

import com.habilisoft.doce.api.auth.base.utils.SortUtils;
import com.habilisoft.doce.api.domain.model.Device;
import com.habilisoft.doce.api.domain.services.DeviceService;
import com.habilisoft.doce.api.persistence.converters.DeviceJpaConverter;
import com.habilisoft.doce.api.persistence.entities.DeviceEntity;
import com.habilisoft.doce.api.persistence.services.DeviceSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    private final DeviceService deviceService;

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
                        SortUtils.processSort(sort, new String[]{"createdDate"})
                )
        );

        return entityPage.map(converter::fromJpaEntity);
    }

    @PutMapping("/{serialNumber}")
    public ResponseEntity<?> edit(@PathVariable String serialNumber, @RequestBody Device device) {
        deviceService.edit(serialNumber, device);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{serialNumber}/synch")
    public ResponseEntity<?> synch(@PathVariable String serialNumber) {
        deviceService.synch(serialNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{serialNumber}/send-fp")
    public ResponseEntity<?> sendFpData(@PathVariable String serialNumber) {
        deviceService.sendFp(serialNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{serialNumber}/send-fp/custom")
    public ResponseEntity<?> sendFpData(
            @RequestBody List<Long> enrollIds,
            @PathVariable String serialNumber
    ) {
        deviceService.sendFp(serialNumber, enrollIds);
        return ResponseEntity.ok().build();
    }
}
