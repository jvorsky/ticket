package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.StopEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StopJpaRepository extends CommonJpaRepository<StopEntity, Long>,
        JpaSpecificationExecutor<StopEntity> {

}
