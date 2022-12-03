package com.armando.timeattendance.api.config.multitenant.jpa;

import com.armando.timeattendance.api.config.multitenant.TenantContext;
import com.armando.timeattendance.api.config.multitenant.http.TenantIdentifierResolver;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Daniel
 */
@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

    @Autowired
    private DataSource dataSource;
    @Value("${tenant-statement}")
    private String schemaStatement;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifie) throws SQLException {
        final Connection connection = getAnyConnection();
        String tenantIdentifier = TenantContext.getCurrentTenant();
        try {
            connection.createStatement().execute(getTenantStatement(tenantIdentifier));
        } catch (SQLException e) {
            throw new HibernateException("Problem setting schema to " + tenantIdentifier, e);
        }
        return connection;
    }

    private String getTenantStatement(String tenantIdentifier) {
        String tenantStatement;
        if (tenantIdentifier != null) {
            tenantStatement = String.format(schemaStatement, tenantIdentifier);
        } else {
            tenantStatement = String.format(schemaStatement, TenantIdentifierResolver.DEFAULT_TENANT_ID);
        }
        return tenantStatement;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try (connection) {
            connection.createStatement().execute(getTenantStatement(null));
        } catch (SQLException e) {
            throw new HibernateException("Problem setting schema to " + tenantIdentifier, e);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }
}
