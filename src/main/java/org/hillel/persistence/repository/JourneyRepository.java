package org.hillel.persistence.repository;

import org.hibernate.Session;
import org.hillel.persistence.entity.JourneyEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class JourneyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Long create(JourneyEntity journeyEntity){
        entityManager.persist(journeyEntity);
        return journeyEntity.getId();
    }

    public Optional<JourneyEntity> findById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return Optional.of(session.find(JourneyEntity.class, id));
    }

    public JourneyEntity save(JourneyEntity journey) {
        return entityManager.merge(journey);
    }
}
