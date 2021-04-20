package org.hillel.service;

import org.hillel.persistence.entity.SeatInfoEntity;
import org.hillel.persistence.repository.SeatInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalSeatInfoService extends AbstractTransactionalService<SeatInfoEntity, Long> {

    private final SeatInfoRepository seatInfoRepository;

    @Autowired
    public TransactionalSeatInfoService(SeatInfoRepository seatInfoRepository) {
        super(seatInfoRepository);
        this.seatInfoRepository = seatInfoRepository;
    }

    @Transactional
    public SeatInfoEntity createOrUpdate(final SeatInfoEntity seatInfo){
        return seatInfoRepository.createOrUpdate(seatInfo);
    }

    @Transactional
    public void remove(SeatInfoEntity seatInfo){
        seatInfoRepository.remove(seatInfo);
    }

    @Transactional
    public void removeById(Long id){
        seatInfoRepository.removeById(id);
    }

}
