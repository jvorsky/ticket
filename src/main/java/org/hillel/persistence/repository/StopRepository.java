package org.hillel.persistence.repository;

import org.hillel.persistence.entity.StopEntity;
import org.springframework.stereotype.Repository;

@Repository
public class StopRepository extends CommonRepository<StopEntity, Long> {

    public StopRepository() {
        super(StopEntity.class);
    }

    @Override
    public void remove(StopEntity entity) {
        StopEntity stop = findById(entity.getId()).get();
        stop.removeAllJourneys();
        getEntityManager().remove(stop.getStopAddInfo());
        super.remove(stop);
    }
}
