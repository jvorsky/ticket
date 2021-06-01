package org.hillel.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public abstract class AbstractTransactionalService<E> {

    private final JpaSpecificationExecutor<E> repository;

    public AbstractTransactionalService(JpaSpecificationExecutor<E> repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Collection<E> findAll(QueryContext queryCntx){
        return repository.findAll(
                getSpecification(queryCntx.getFilterKey(), queryCntx.getFilterValue()),
                queryCntx.getPageRequest()
        ).getContent();
    }

    protected Specification<E> getSpecification(String filterKey, String filterValue){
        return null;
    }

}
