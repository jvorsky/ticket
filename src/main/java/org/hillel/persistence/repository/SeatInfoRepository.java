package org.hillel.persistence.repository;

import org.hillel.persistence.entity.SeatInfoEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SeatInfoRepository extends CommonRepository<SeatInfoEntity, Long> {

    public SeatInfoRepository() {
        super(SeatInfoEntity.class);
    }
}
