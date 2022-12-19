package com.habilisoft.doce.api.persistence.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created on 18/12/22.
 */
@Data
@Entity
@Table(name = "company_info")
public class CompanyInfoEntity {
    @Id
    private String name;
}
