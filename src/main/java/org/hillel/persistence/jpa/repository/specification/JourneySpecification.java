package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.JourneyEntity_;
import org.hillel.persistence.entity.VehicleEntity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.*;

public interface JourneySpecification {

    static Specification<JourneyEntity> byActiveVehicle() {
        return (root, query, criteriaBuilder) -> {
            Join<Object, Object> vehicle = root.join(JourneyEntity_.VEHICLE);
            return criteriaBuilder.isTrue(vehicle.get(VehicleEntity_.ACTIVE));
        };
    }

    static Specification<JourneyEntity> byCreateDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            Instant start = LocalDateTime.of(date, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant();
            Instant end = LocalDateTime.of(date, LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
            return criteriaBuilder.between(root.get(JourneyEntity_.CREATE_DATE), start, end);
        };
    }

    static Specification<JourneyEntity> byStationFrom(final String stationFrom){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(JourneyEntity_.STATION_FROM), criteriaBuilder.literal(stationFrom));
    }

    static Specification<JourneyEntity> byStationTo(final String stationTo){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(JourneyEntity_.STATION_TO), criteriaBuilder.literal(stationTo));
    }
}
