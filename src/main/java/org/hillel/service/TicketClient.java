package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TicketClient {

    @Autowired
    private List<JourneyService> journeyServices;

    @Autowired
    private TransactionalJourneyService journeyService;

    @Autowired
    private TransactionalStopService stopService;

    @Autowired
    private Environment environment;

    @Value("${datasource.url}")
    private String url;

    public TicketClient(){
    }

    public Long createJourney(final JourneyEntity journeyEntity){
        return journeyService.createJourney(journeyEntity);
    }

    public Optional<JourneyEntity> getJourneyById(Long id, boolean withDependencies){
        return id == null ? Optional.empty() : journeyService.getById(id, withDependencies);
    }

    public Long createStop(final StopEntity stopEntity){
        return stopService.create(stopEntity);
    }

    public void saveJourney(JourneyEntity journey) {
        journeyService.save(journey);
    }
}
