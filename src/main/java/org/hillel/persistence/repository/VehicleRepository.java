package org.hillel.persistence.repository;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.hillel.persistence.entity.JourneyEntity_;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.VehicleEntity_;
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
        /*
        return entityManager.createQuery("from " + VehicleEntity.class.getName() +
                " where name = :nameParam and active = :activeParam", VehicleEntity.class)
                .setParameter("nameParam", name)
                .setParameter("activeParam", true)
                .getResultList();
         */
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<VehicleEntity> query = criteriaBuilder.createQuery(VehicleEntity.class);
        final Root<VehicleEntity> vehicle = query.from(VehicleEntity.class);
        Join<Object, Object> journeys = vehicle.join(VehicleEntity_.JOURNEYS, JoinType.LEFT);
        final Predicate byJourneyName = criteriaBuilder.equal(journeys.get(JourneyEntity_.STATION_FROM), criteriaBuilder.literal("Odessa"));
        journeys.on(byJourneyName);

        final Predicate byName = criteriaBuilder.equal(vehicle.get(VehicleEntity_.NAME), criteriaBuilder.parameter(String.class, "nameParam"));
        final Predicate active = criteriaBuilder.equal(vehicle.get(VehicleEntity_.ACTIVE), criteriaBuilder.parameter(Boolean.class, "activeParam"));
        return entityManager.createQuery(query.
                select(vehicle).
                where(byName, active)
                .orderBy(new OrderImpl(vehicle.get(VehicleEntity_.ID), false)))
                .setParameter("nameParam", name)
                .setParameter("activeParam", true)
                .getResultList();
//        return entityManager.createQuery(
//                "select v from VehicleEntity v left join v.journeys js on js.vehicle.id = v.id order by v.id asc ", VehicleEntity.class)
//                .setFirstResult(1)
//                .setMaxResults(2)
//                .getResultList();
//        return entityManager.createQuery(
//                "select v from VehicleEntity v left join v.journeys js on js.vehicleEntity.id = v.id and js.stationFrom = :stationFrom order by v.id asc ", VehicleEntity.class)
//                .setParameter("stationFrom", "Odessa")
//                .setFirstResult(1)
//                .setMaxResults(2)
//                .getResultList();
    }

}
