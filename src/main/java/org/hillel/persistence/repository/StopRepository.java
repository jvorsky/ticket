package org.hillel.persistence.repository;

import org.hillel.persistence.entity.StopEntity;
import org.springframework.stereotype.Repository;

@Repository
public class StopRepository extends CommonRepository<StopEntity, Long> {

    public StopRepository() {
        super(StopEntity.class);
    }

}
