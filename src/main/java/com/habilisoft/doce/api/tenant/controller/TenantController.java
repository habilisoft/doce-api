package com.habilisoft.doce.api.tenant.controller;

import com.habilisoft.doce.api.tenant.model.http.TenantRequest;
import com.habilisoft.doce.api.tenant.model.http.TenantResponse;
import com.habilisoft.doce.api.tenant.service.TenantService;
import liquibase.exception.LiquibaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Daniel
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TenantRequest tenantRequest) throws LiquibaseException {
        TenantResponse created = tenantService.create(tenantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TenantResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(tenantService.findById(id));
    }
}
