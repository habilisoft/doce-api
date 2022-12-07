package com.habilisoft.doce.api.persistence;

import com.habilisoft.doce.api.auth.base.model.jpa.WithId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreRemove;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 21/5/2017.
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
@Where(clause="deleted='false'")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public abstract class BaseEntity implements Serializable, WithId {
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private Date lastModifiedDate;
    @CreatedDate
    private Date createdDate;
    @CreatedBy
    private String createdBy;
    @Column
    @JsonIgnore
    private Boolean deleted = false;

    @PreRemove
    public void softDelete() {
        this.deleted = true;
    }

}
