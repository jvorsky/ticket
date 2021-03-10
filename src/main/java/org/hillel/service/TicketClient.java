package org.hillel.service;

import org.hillel.Journey;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Collection;


public class TicketClient {

    private JourneyService journeyService;


    public TicketClient(JourneyService journeyService) {
        this.journeyService = journeyService;
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

        return journeyService.find(stationFrom, stationTo, dateFrom, dateTo);
    }

}
