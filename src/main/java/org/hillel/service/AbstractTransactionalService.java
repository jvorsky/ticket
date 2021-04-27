package org.hillel.service;

import org.hillel.persistence.entity.AbstractEntity;
import org.hillel.persistence.repository.CommonRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

public abstract class AbstractTransactionalService<E extends AbstractEntity<ID>, ID extends Serializable> {

    private final CommonRepository<E, ID> repository;

    public AbstractTransactionalService(CommonRepository<E, ID> repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Collection<E> findAll(QueryContext queryCntx){
        QueryType queryType = queryCntx.getQueryType();
        switch (queryType){
            case HQL: return findAllAsHQL(queryCntx);
            case NATIVE:return findAllAsNative(queryCntx);
            case NAMED:return findAllAsNamed(queryCntx);
            case CRITERIA:return findAllAsCriteria(queryCntx);
            case STORED_PROCEDURE:return findAllAsStoredProcedure(queryCntx);
            default:
                throw new IllegalArgumentException("unknown queryType");
        }
    }

    @Transactional(readOnly = true)
    public Collection<E> findAllAsHQL(QueryContext queryCntx){
        return repository.findAll(queryCntx);
    }

    @Transactional(readOnly = true)
    public Collection<E> findAllAsNative(QueryContext queryCntx){
        return repository.findAllAsNative(queryCntx);
    }

    @Transactional(readOnly = true)
    public Collection<E> findAllAsNamed(QueryContext queryCntx){
        return repository.findAllAsNamed(queryCntx);
    }

    @Transactional(readOnly = true)
    public Collection<E> findAllAsCriteria(QueryContext queryCntx){
        return repository.findAllAsCriteria(queryCntx);
    }

    @Transactional(readOnly = true)
    public Collection<E> findAllAsStoredProcedure(QueryContext queryCntx){
        return repository.findAllAsStoredProcedure(queryCntx);
    }
}
