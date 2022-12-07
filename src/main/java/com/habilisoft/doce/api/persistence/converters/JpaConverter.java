package com.habilisoft.doce.api.persistence.converters;

/**
 * Created on 2021-01-18.
 * @param D Domain class
 * @param E Jpa class
 */
public interface JpaConverter<D, E> {
    D fromJpaEntity(E jpaEntity);
    E toJpaEntity(D domainObject);
}
