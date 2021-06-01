package org.hillel.service;

import org.hillel.persistence.entity.SeatInfoEntity;
import org.hillel.persistence.jpa.repository.SeatInfoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalSeatInfoService extends AbstractTransactionalService<SeatInfoEntity> {

    private final SeatInfoJpaRepository seatInfoRepository;

    @Autowired
    public TransactionalSeatInfoService(SeatInfoJpaRepository seatInfoRepository) {
        super(seatInfoRepository);
        this.seatInfoRepository = seatInfoRepository;
    }

    @Transactional
    public SeatInfoEntity createOrUpdate(final SeatInfoEntity seatInfo){
        return seatInfoRepository.save(seatInfo);
    }

    @Transactional
    public void remove(SeatInfoEntity seatInfo){
        seatInfoRepository.delete(seatInfo);
    }

    @Transactional
    public void removeById(Long id){
        seatInfoRepository.deleteById(id);
    }

}
