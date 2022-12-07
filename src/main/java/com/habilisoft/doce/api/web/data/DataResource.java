package com.habilisoft.doce.api.web.data;

import com.habilisoft.doce.api.domain.model.WorkShiftDay;
import com.habilisoft.doce.api.serialization.BaseEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 11/11/22.
 */
@RestController
@RequestMapping("/data")
public class DataResource {

    @GetMapping
    public ResponseEntity<?> getData() {
        Data data = Data.builder()
                .days(WorkShiftDay.values())
                .build();
        return ResponseEntity.ok(data);
    }

    @lombok.Data
    @Builder
    public static class Data {
        @JsonSerialize(contentConverter = BaseEnumConverter.class)
        WorkShiftDay[] days;
    }
}
