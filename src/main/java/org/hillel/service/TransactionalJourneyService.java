package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.repository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionalJourneyService {

    @Autowired
    private JourneyRepository journeyRepository;

    @Transactional
    public JourneyEntity createOrUpdate(final JourneyEntity entity){
        return journeyRepository.createOrUpdate(entity);
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

    @Transactional
    public void removeById(Long id) {
        journeyRepository.removeById(id);
    }

    @Transactional
    public void remove(JourneyEntity journey) {
        journeyRepository.remove(journey);
    }

}
