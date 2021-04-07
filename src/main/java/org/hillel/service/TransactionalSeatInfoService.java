package org.hillel.service;

import org.hillel.persistence.entity.SeatInfoEntity;
import org.hillel.persistence.repository.SeatInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalSeatInfoService {

    @Autowired
    private SeatInfoRepository seatInfoRepository;

    @Transactional
    public SeatInfoEntity createOrUpdate(final SeatInfoEntity seatInfo){
        return seatInfoRepository.createOrUpdate(seatInfo);
    }

}
