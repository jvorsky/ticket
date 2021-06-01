package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.AbstractEntity_;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.VehicleEntity_;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.*;

public enum VehicleSpecification implements SpecificationCreator<VehicleEntity>{

    NAME {
        @Override
        public Specification<VehicleEntity> getSpecification(String filterValue) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(VehicleEntity_.NAME), criteriaBuilder.literal(filterValue));
        }
    },
    CREATE_DATE {
        @Override
        public Specification<VehicleEntity> getSpecification(String filterValue) {
            return (root, query, criteriaBuilder) -> {
                LocalDate date = LocalDate.parse(filterValue);
                Instant start = LocalDateTime.of(date, LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant();
                Instant end = LocalDateTime.of(date, LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
                return criteriaBuilder.between(root.get(VehicleEntity_.CREATE_DATE), start, end);
            };
        }
    },
    ID {
        @Override
        public Specification<VehicleEntity> getSpecification(String filterValue) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(AbstractEntity_.ID), criteriaBuilder.literal(filterValue));
        }
    },
    ONLY_ACTIVE {
        @Override
        public Specification<VehicleEntity> getSpecification(String filterValue) {
            return (root, query, criteriaBuilder)->
                    criteriaBuilder.isTrue(root.get(VehicleEntity_.ACTIVE));
        }
    };

    static Specification<VehicleEntity> byNameAndExample(final String name, final  VehicleEntity vehicle){
        return (root, query, criteriaBuilder) ->{
            final Predicate byName = criteriaBuilder.equal(root.get(VehicleEntity_.NAME), criteriaBuilder.literal(name));
            final Predicate byExample = QueryByExamplePredicateBuilder.getPredicate(root, criteriaBuilder, Example.of(vehicle));
            return criteriaBuilder.and(byName, byExample);
        };
    }


}
