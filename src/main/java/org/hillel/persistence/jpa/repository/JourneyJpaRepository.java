package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.JourneyEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JourneyJpaRepository extends CommonJpaRepository<JourneyEntity, Long>,
        JpaSpecificationExecutor<JourneyEntity> {

}
