package com.armando.timeattendance.api.tenant.repository;

import com.armando.timeattendance.api.tenant.model.jpa.Tenant;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository
public interface TenantRepository extends PagingAndSortingRepository<Tenant, String> {

}
