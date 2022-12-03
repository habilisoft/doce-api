package com.armando.timeattendance.api.web.data;

import com.armando.timeattendance.api.domain.model.WorkShiftDay;
import com.armando.timeattendance.api.serialization.BaseEnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
