package org.hillel.persistence.repository;

import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.Collection;

@Repository
public class VehicleRepository extends CommonRepository<VehicleEntity, Long> {

    public VehicleRepository() {
        super(VehicleEntity.class);
    }

    @Override
    public void remove(VehicleEntity entity) {
        entity = findById(entity.getId()).get();
        entity.removeAllJourney();
        getEntityManager()
                .createQuery("delete from SeatInfoEntity where vehicle = :vehicleParam")
                .setParameter("vehicleParam", entity)
                .executeUpdate();
        super.remove(entity);
    }

    public Collection<VehicleEntity> findByName(String name){
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<VehicleEntity> query = criteriaBuilder.createQuery(VehicleEntity.class);
        final Root<VehicleEntity> from = query.from(VehicleEntity.class);
        Join<Object, Object> journeys = from.join("journeys", JoinType.LEFT);
        final Predicate byName = criteriaBuilder.equal(from.get("name"), criteriaBuilder.literal(name));
        final Predicate active = criteriaBuilder.equal(from.get("active"), criteriaBuilder.literal(true));
        final Predicate byJourneyName = criteriaBuilder.equal(journeys.get("stationFrom"), criteriaBuilder.literal("from 1"));
        return entityManager.createQuery(query.
                select(from).
                where(byName, active, byJourneyName)
        ).getResultList();
    }

}
