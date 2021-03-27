package org.hillel.service;

import org.hillel.Journey;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    public Long createStop(final StopEntity stopEntity){
        return stopService.create(stopEntity);
    }

    public Collection<Journey> find(String stationFrom, String stationTo, LocalDate dateFrom, LocalDate dateTo) throws Exception {
        if (!StringUtils.hasText(stationFrom))
            throw new IllegalArgumentException("stationFrom must be set");
        if (!StringUtils.hasText(stationTo))
            throw new IllegalArgumentException("stationTo must be set");
        if (dateFrom == null)
            throw new IllegalArgumentException("dateFrom must be set");
        if (dateTo == null)
            throw new IllegalArgumentException("dateTo must be set");

//        List<Journey> rez = new ArrayList<>();
//        return journeyServices.get("dbJourneyService").find(stationFrom, stationTo, dateFrom, dateTo);
        for (JourneyService service : journeyServices){
            System.out.println(service.getClass().getName());
//            rez.addAll(service.find(stationFrom, stationTo, dateFrom, dateTo));
        }
//        return rez;
        return Collections.emptyList();
    }

    @PostConstruct
    public void doPost(){
        System.out.println("post construct");
        //System.out.println(environment.getProperty("datasource.url"));
        System.out.println(url);
    }

    @PreDestroy
    public void destroy(){
        System.out.println("destroy");
    }
}
