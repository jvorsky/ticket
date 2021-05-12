package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.JourneyEntity_;
import org.hillel.persistence.entity.VehicleEntity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public interface JourneySpecification {

    static Specification<JourneyEntity> byActiveVehicle() {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> vehicle = root.join(JourneyEntity_.VEHICLE);
            return criteriaBuilder.isTrue(vehicle.get(VehicleEntity_.ACTIVE));
        };
    }

    static Specification<JourneyEntity> byCreateDate(Instant date) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.between(root.get(JourneyEntity_.CREATE_DATE), date, date.plusSeconds(
                    TimeUnit.SECONDS.convert(1, TimeUnit.DAYS)
            ));
    }
}
