package com.habilisoft.doce.api.scheduler.services;

import com.habilisoft.doce.api.tenant.model.jpa.Tenant;
import com.habilisoft.doce.api.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2020-11-18.
 */
@Component
@RequiredArgsConstructor
public class ScheduleLoader implements CommandLineRunner {
    private final ReportScheduler notificationScheduler;
    private final TenantRepository tenantRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Tenant> tenants = tenantRepository.findAll();
        tenants.forEach(tenant -> notificationScheduler.loadReminders(tenant));
    }
}
