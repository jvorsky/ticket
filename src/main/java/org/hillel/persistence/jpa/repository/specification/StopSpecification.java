package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.*;

public interface StopSpecification {

    static Specification<StopEntity> byName(final String name){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(StopEntity_.COMMON_INFO).get(CommonInfo_.NAME), criteriaBuilder.literal(name));
    }

    static Specification<StopEntity> byCreateDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            Instant start = LocalDateTime.of(date, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant();
            Instant end = LocalDateTime.of(date, LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
            return criteriaBuilder.between(root.get(StopEntity_.CREATE_DATE), start, end);
        };
    }

}
