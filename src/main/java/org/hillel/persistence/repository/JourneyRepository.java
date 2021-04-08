package org.hillel.persistence.repository;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class JourneyRepository extends CommonRepository<JourneyEntity, Long> {

    public JourneyRepository() {
        super(JourneyEntity.class);
    }

    @Override
    public JourneyEntity createOrUpdate(JourneyEntity entity) {
        final VehicleEntity vehicle = entity.getVehicle();
        if (Objects.nonNull(vehicle)){
            if (!entityManager.contains(vehicle)){
                entity.setVehicle(entityManager.merge(vehicle));
            }
        }
        return super.createOrUpdate(entity);
    }
}
