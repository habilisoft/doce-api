package com.habilisoft.doce.api.tenant.service;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Daniel
 */
@Service
public class TenantDatabaseService {

    private static final String DDL_CREATE_SCHEMA = "CREATE SCHEMA IF NOT EXISTS %s";
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private LiquibaseProperties liquibaseProperties;
    @Value("${change-log.tenant}")
    private String changeLogPath;

    public void create(String tenant) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(DDL_CREATE_SCHEMA, tenant));
        } catch (SQLException e) {
            throw new RuntimeException("Can not connect to database", e);
        }
    }

    public void migrate(String tenantKey) throws LiquibaseException {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLogPath);
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(tenantKey);
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(true);
        liquibase.afterPropertiesSet();
    }
}
