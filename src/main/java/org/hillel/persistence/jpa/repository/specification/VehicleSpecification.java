package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.VehicleEntity_;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public interface VehicleSpecification {

    static Specification<VehicleEntity> byName(final String name){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(VehicleEntity_.NAME), criteriaBuilder.literal(name));
    }

    static Specification<VehicleEntity> byNameAndExample(final String name, final  VehicleEntity vehicle){
        return (root, query, criteriaBuilder) ->{
            final Predicate byName = criteriaBuilder.equal(root.get(VehicleEntity_.NAME), criteriaBuilder.literal(name));
            final Predicate byExample = QueryByExamplePredicateBuilder.getPredicate(root, criteriaBuilder, Example.of(vehicle));
            return criteriaBuilder.and(byName, byExample);
        };
    }

    static Specification<VehicleEntity> onlyActive(){
        return (root, query, criteriaBuilder)->
                criteriaBuilder.isTrue(root.get(VehicleEntity_.ACTIVE));

    }
}
