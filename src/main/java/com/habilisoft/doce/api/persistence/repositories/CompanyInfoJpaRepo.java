package com.habilisoft.doce.api.persistence.repositories;

import com.habilisoft.doce.api.persistence.entities.CompanyInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created on 18/12/22.
 */
public interface CompanyInfoJpaRepo extends JpaRepository<CompanyInfoEntity, String> {
}
