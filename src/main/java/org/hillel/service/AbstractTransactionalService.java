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
    public Collection<E> findAll(QueryType queryType){
        switch (queryType){
            case HQL: return findAll();
            case NATIVE:return findAllAsNative();
            case NAMED:return findAllAsNamed();
            case CRITERIA:return findAllAsCriteria();
            case STORED_PROCEDURE:return findAllAsStoredProcedure();
            default:
                throw new IllegalArgumentException("unknown queryType");
        }
    }

    @Transactional(readOnly = true)
    public Collection<E> findAll(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<E> findAllAsNative(){
        return repository.findAllAsNative();
    }

    @Transactional(readOnly = true)
    public Collection<E> findAllAsNamed(){
        return repository.findAllAsNamed();
    }

    @Transactional(readOnly = true)
    public Collection<E> findAllAsCriteria(){
        return repository.findAllAsCriteria();
    }

    @Transactional(readOnly = true)
    public Collection<E> findAllAsStoredProcedure(){
        return repository.findAllAsStoredProcedure();
    }
}
