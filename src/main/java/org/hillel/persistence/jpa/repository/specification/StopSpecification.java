package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.CommonInfo_;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.StopEntity_;
import org.springframework.data.jpa.domain.Specification;

import java.time.*;

public enum StopSpecification implements SpecificationCreator<StopEntity>{

    NAME {
        @Override
        public Specification<StopEntity> getSpecification(String filterValue) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(StopEntity_.COMMON_INFO).get(CommonInfo_.NAME), criteriaBuilder.literal(filterValue));
        }
    },
    CREATE_DATE {
        @Override
        public Specification<StopEntity> getSpecification(String filterValue) {
            return (root, query, criteriaBuilder) -> {
                LocalDate date = LocalDate.parse(filterValue);
                Instant start = LocalDateTime.of(date, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant();
                Instant end = LocalDateTime.of(date, LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
                return criteriaBuilder.between(root.get(StopEntity_.CREATE_DATE), start, end);
            };
        }
    };

}
