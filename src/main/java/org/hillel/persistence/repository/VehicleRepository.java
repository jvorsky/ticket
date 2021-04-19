package org.hillel.persistence.repository;

import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.stereotype.Repository;

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

//    @Override
//    public Collection<VehicleEntity> findAll() {
//        //return entityManager.createNamedQuery("findAll", VehicleEntity.class).getResultList();
//        //return entityManager.createNamedStoredProcedureQuery("findAll").getResultList();
//    }
}
