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
        entity.getSeatInfos().forEach(seatInfo -> getEntityManager().remove(seatInfo));
        super.remove(entity);
    }
}
