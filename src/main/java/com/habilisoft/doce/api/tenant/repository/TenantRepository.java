package com.habilisoft.doce.api.tenant.repository;

import com.habilisoft.doce.api.tenant.model.jpa.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {

}
