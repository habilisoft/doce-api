package com.armando.timeattendance.api.tenant.service;

import com.armando.timeattendance.api.tenant.exception.TenantDuplicatedException;
import com.armando.timeattendance.api.tenant.exception.TenantNotFoundException;
import com.armando.timeattendance.api.tenant.model.http.TenantRequest;
import com.armando.timeattendance.api.tenant.model.http.TenantResponse;
import com.armando.timeattendance.api.tenant.model.jpa.Tenant;
import com.armando.timeattendance.api.tenant.repository.TenantRepository;
import liquibase.exception.LiquibaseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Daniel
 */
@Service
public class TenantService {

    @Autowired
    private TenantDatabaseService tenantDatabaseService;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private ModelMapper modelMapper;

    public TenantResponse create(TenantRequest tenantRequest) throws LiquibaseException {
        tenantRepository.findById(tenantRequest.getName()).ifPresent((t) -> {
            throw new TenantDuplicatedException(String.format("tenant[%s] already exist", t.getName()));
        });
        Tenant tenant = modelMapper.map(tenantRequest, Tenant.class);
        Tenant saved = tenantRepository.save(tenant);
        tenantDatabaseService.create(saved.getName());
        tenantDatabaseService.migrate(saved.getName());
        return modelMapper.map(saved, TenantResponse.class);
    }

    public TenantResponse findById(String id) {
        return tenantRepository.findById(id).map(m -> modelMapper.map(m, TenantResponse.class))
                .orElseThrow(() -> {
                    return new TenantNotFoundException(String.format("tenant[%s] not found", id));
                });
    }
}
