package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.JourneyEntity_;
import org.hillel.persistence.entity.VehicleEntity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.*;

public enum JourneySpecification implements SpecificationCreator<JourneyEntity> {

    ACTIVE_VEHICLE {
        @Override
        public Specification<JourneyEntity> getSpecification(String filterValue) {
            return (root, query, criteriaBuilder) -> {
                Join<Object, Object> vehicle = root.join(JourneyEntity_.VEHICLE);
                return criteriaBuilder.isTrue(vehicle.get(VehicleEntity_.ACTIVE));
            };
        }
    },
    STATION_FROM {
        @Override
        public Specification<JourneyEntity> getSpecification(String filterValue) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(JourneyEntity_.STATION_FROM), criteriaBuilder.literal(filterValue));
        }
    },
    STATION_TO {
        @Override
        public Specification<JourneyEntity> getSpecification(String filterValue) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(JourneyEntity_.STATION_TO), criteriaBuilder.literal(filterValue));
        }
    },
    CREATE_DATE {
        @Override
        public Specification<JourneyEntity> getSpecification(String filterValue) {
            return (root, query, criteriaBuilder) -> {
                LocalDate date = LocalDate.parse(filterValue);
                Instant start = LocalDateTime.of(date, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant();
                Instant end = LocalDateTime.of(date, LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
                return criteriaBuilder.between(root.get(JourneyEntity_.CREATE_DATE), start, end);
            };
        }
    };

}
