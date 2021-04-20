package org.hillel.service;

import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalStopService extends AbstractTransactionalService<StopEntity, Long> {

    private final StopRepository stopRepository;

    @Autowired
    public TransactionalStopService(StopRepository stopRepository) {
        super(stopRepository);
        this.stopRepository = stopRepository;
    }

    @Transactional
    public StopEntity createOrUpdate(StopEntity stopEntity){
        return stopRepository.createOrUpdate(stopEntity);
    }

    @Transactional
    public void remove(StopEntity stop) {
        stopRepository.remove(stop);
    }
}
