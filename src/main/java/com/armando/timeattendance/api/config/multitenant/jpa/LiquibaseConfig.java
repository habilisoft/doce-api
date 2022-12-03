package com.armando.timeattendance.api.config.multitenant.jpa;


import com.armando.timeattendance.api.tenant.model.jpa.Tenant;
import com.armando.timeattendance.api.tenant.repository.TenantRepository;
import com.armando.timeattendance.api.tenant.service.TenantDatabaseService;
import liquibase.integration.spring.MultiTenantSpringLiquibase;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 */
@Configuration
@EnableConfigurationProperties(LiquibaseProperties.class)
public class LiquibaseConfig {

    @Value("${change-log.tenant}")
    private String changeLogTenantPath;
    @Value("${spring.liquibase.change-log}")
    private String changeLogPath;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, LiquibaseProperties liquibaseProperties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLogPath);
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        return liquibase;
    }

    @Bean
    @DependsOn("liquibase")
    public MultiTenantSpringLiquibase multiTenantLiquibase(ResourceLoader resourceLoader, DataSource dataSource, LiquibaseProperties liquibaseProperties,
                                                           TenantRepository tenantRepository, TenantDatabaseService tenantDatabaseService) {
        MultiTenantSpringLiquibase liquibase = new SchemaMultiTenantSpringLiquibase();
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLogTenantPath);
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        final List<String> schemas = getSchemas(tenantRepository);

        schemas.forEach(schema -> {
            tenantDatabaseService.create(schema);
            liquibase.setDefaultSchema(schema);
        });

        liquibase.setSchemas(schemas);
        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        return liquibase;
    }

    private List<String> getSchemas(TenantRepository tenantRepository) {
        List<String> list = new ArrayList<>();
        for (Tenant tenant : tenantRepository.findAll()) {
            list.add(tenant.getName());
        }
        return list;
    }
}
