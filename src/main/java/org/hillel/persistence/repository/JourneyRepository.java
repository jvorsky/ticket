package org.hillel.persistence.repository;

import org.hillel.persistence.entity.JourneyEntity;
import org.springframework.stereotype.Repository;

@Repository
public class JourneyRepository extends CommonRepository<JourneyEntity, Long> {

    public JourneyRepository() {
        super(JourneyEntity.class);
    }
}
