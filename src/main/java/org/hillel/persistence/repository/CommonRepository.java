package org.hillel.persistence.repository;

import org.hibernate.Session;
import org.hillel.persistence.entity.AbstractEntity;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public abstract class CommonRepository<E extends AbstractEntity<ID>, ID extends Serializable> implements GenericRepository<E, ID>{

    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<E> entityClass;

    public CommonRepository(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public E createOrUpdate(E entity) {
        Assert.notNull(entity, "entity must be set");
        if (Objects.isNull(entity.getId())) {
            entityManager.persist(entity);
        } else {
            return entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public Optional<E> findById(ID id) {
        if (Objects.isNull(id)) return Optional.empty();
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public void removeById(ID id) {
        entityManager.remove(entityManager.getReference(entityClass, id));
    }

    @Override
    public void remove(E entity) {
        if (entityManager.contains(entity)){
            entityManager.remove(entity);
        } else {
            removeById(entity.getId());
        }
    }

    public Collection<E> findByIds(ID... ids){
        return entityManager.unwrap(Session.class).byMultipleIds(entityClass).multiLoad(ids);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Collection<E> findByName(String name){
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        final Root<E> from = query.from(entityClass);
        Join<Object, Object> journeys = from.join("journeys", JoinType.LEFT);
        final Predicate byName = criteriaBuilder.equal(from.get("name"), criteriaBuilder.literal(name));
        final Predicate active = criteriaBuilder.equal(from.get("active"), criteriaBuilder.literal(true));
        final Predicate byJourneyName = criteriaBuilder.equal(journeys.get("stationFrom"), criteriaBuilder.literal("from 1"));
        return entityManager.createQuery(query.
                select(from).
                where(byName, active, byJourneyName)
                ).getResultList();
    }

    @Override
    public Collection<E> findAll() {
        //return entityManager.createQuery("select j from " + entityClass.getName() + " j ", entityClass).getResultList();
//        return entityManager.createNativeQuery(
//                "select * from " + entityClass.getAnnotation(Table.class).name(), entityClass).getResultList();
//        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        final CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
//        final Root<E> from = query.from(entityClass);
//        return entityManager.createQuery(query.select(from)).getResultList();
        return entityManager.createStoredProcedureQuery("find_all", entityClass).
                registerStoredProcedureParameter(1, Class.class, ParameterMode.REF_CURSOR).
                registerStoredProcedureParameter(2, String.class, ParameterMode.IN).
                setParameter(2, entityClass.getAnnotation(Table.class).name()).
                getResultList();
    }
}
