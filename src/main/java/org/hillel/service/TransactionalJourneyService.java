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
    public Long createJourney(final JourneyEntity entity){
        return journeyRepository.create(entity);
    }

    @Transactional(readOnly = true)
    public Optional<JourneyEntity> getById(Long id, boolean withDependencies) {
        final Optional<JourneyEntity> byId = journeyRepository.findById(id);
        if (withDependencies && byId.isPresent()){
            byId.get().getVehicle().getName();
            byId.get().getStops().size();
        }
        return byId;
    }

    @Transactional
    public void save(JourneyEntity journey) {
        final JourneyEntity save = journeyRepository.save(journey);
        save.setStationTo("station to");

    }
}
