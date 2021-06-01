package org.hillel.service;

import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.jpa.repository.StopJpaRepository;
import org.hillel.persistence.jpa.repository.specification.StopSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class TransactionalStopService extends AbstractTransactionalService<StopEntity> {

    private final StopJpaRepository stopRepository;

    @Autowired
    public TransactionalStopService(StopJpaRepository stopRepository) {
        super(stopRepository);
        this.stopRepository = stopRepository;
    }

    @Transactional
    public StopEntity createOrUpdate(StopEntity stopEntity){
        return stopRepository.save(stopEntity);
    }

    @Transactional
    public void remove(StopEntity stop) {
        stopRepository.findById(stop.getId()).ifPresent(stopEntity -> {
            stopEntity.removeAllJourneys();
            stopRepository.delete(stopEntity);
        });
    }

    @Override
    protected Specification<StopEntity> getSpecification(String filterKey, String filterValue) {
        if ("Name".equals(filterKey)){
            return StopSpecification.byName(filterValue);
        } else if ("CreateDate".equals(filterKey)){
            return StopSpecification.byCreateDate(LocalDate.parse(filterValue));
        } else {
            return super.getSpecification(filterKey, filterValue);
        }
    }

}
