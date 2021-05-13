package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.jpa.repository.JourneyJpaRepository;
import org.hillel.persistence.jpa.repository.specification.JourneySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionalJourneyService extends AbstractTransactionalService<JourneyEntity, Long> {

    private final JourneyJpaRepository journeyRepository;

    @Autowired
    public TransactionalJourneyService(JourneyJpaRepository journeyRepository) {
        super(journeyRepository);
        this.journeyRepository = journeyRepository;
    }

    @Transactional
    public JourneyEntity createOrUpdate(final JourneyEntity entity){
        return journeyRepository.save(entity);
    }

    @Transactional
    public Optional<JourneyEntity> findById(Long id, boolean withDependencies) {
        final Optional<JourneyEntity> byId = journeyRepository.findById(id);
        if (withDependencies && byId.isPresent()){
            byId.get().getVehicle().getName();
            byId.get().getStops().size();
        }
        return byId;
    }

    @Transactional(readOnly = true)
    public List<JourneyEntity> findAllByActiveVehicle(QueryContext queryContext){
        return journeyRepository.findAll(
                JourneySpecification.byActiveVehicle(), queryContext.getPageRequest()).getContent();
    }

    @Transactional(readOnly = true)
    public List<JourneyEntity> findAllByCreateDate(LocalDate date, QueryContext queryContext){
        return journeyRepository.findAll(
                JourneySpecification.byCreateDate(date), queryContext.getPageRequest()).getContent();
    }

    @Transactional
    public void removeById(Long id) {
        journeyRepository.disableById(id);
    }

    @Transactional
    public void remove(JourneyEntity journey) {
        journeyRepository.delete(journey);
    }

}
