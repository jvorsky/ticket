package org.hillel.service;

import org.hillel.persistence.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

public abstract class AbstractTransactionalService<E extends AbstractEntity<ID>, ID extends Serializable> {

    private final JpaRepository<E, ID> repository;

    public AbstractTransactionalService(JpaRepository<E, ID> repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Collection<E> findAll(QueryContext queryCntx){
        QueryType queryType = queryCntx.getQueryType();
        switch (queryType){
            case JPQL: return findAllAsJPQL(queryCntx);
//            case NATIVE:return findAllAsNative(queryCntx);
//            case NAMED:return findAllAsNamed(queryCntx);
//            case CRITERIA:return findAllAsCriteria(queryCntx);
//            case STORED_PROCEDURE:return findAllAsStoredProcedure(queryCntx);
            default:
                throw new IllegalArgumentException("unknown queryType");
        }
    }

    @Transactional(readOnly = true)
    public Collection<E> findAllAsJPQL(QueryContext queryCntx){
        return repository.findAll(queryCntx.getPageRequest()).getContent();
    }

}
