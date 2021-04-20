package org.hillel.persistence.repository;

import org.hibernate.Session;
import org.hillel.persistence.entity.AbstractEntity;
import org.springframework.util.Assert;

import javax.persistence.*;
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

    // метод поиска через HQL
    public Collection<E> findAll(){
        return entityManager.createQuery("select j from " + entityClass.getName() + " j ", entityClass).getResultList();
    }

    // метод поиска через вызов sql запроса
    public Collection<E> findAllAsNative(){
        return entityManager.createNativeQuery(
                "select * from " + entityClass.getAnnotation(Table.class).name(), entityClass).getResultList();
    }

    // метод поиска через вызов именованного запроса по алиасу
    public Collection<E> findAllAsNamed(){
        return entityManager.createNamedQuery(
                entityClass.getAnnotation(NamedQueries.class).value()[0].name(), entityClass).getResultList();
    }

    // метод поиска через CriteriaBuilder
    public Collection<E> findAllAsCriteria(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        Root<E> from = query.from(entityClass);
        return entityManager.createQuery(query.select(from)).getResultList();
    }

    // метод поиска через вызов хранимой функции
    public Collection<E> findAllAsStoredProcedure(){
        return entityManager.createStoredProcedureQuery("find_all", entityClass).
                registerStoredProcedureParameter(1, Class.class, ParameterMode.REF_CURSOR).
                registerStoredProcedureParameter(2, String.class, ParameterMode.IN).
                setParameter(2, entityClass.getAnnotation(Table.class).name()).
                getResultList();
    }

}
