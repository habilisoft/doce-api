package com.armando.timeattendance.api.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2020-09-01.
 */
@RestController
@RequestMapping("/healthcheck")
public class HealthCheckResource {
    @GetMapping
    ResponseEntity<?> healthCheck(){
        return ResponseEntity.ok().build();
    }
}
