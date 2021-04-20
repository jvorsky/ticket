package org.hillel.service.old;

import org.hillel.Journey;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryJourneyServiceImpl implements JourneyService {

    private final Map<String, List<Journey>> storage = new HashMap<>();

    public InMemoryJourneyServiceImpl() {
        System.out.println("constructor InMemoryJourneyServiceImpl ");
    }

    {
        storage.put("Odessa->Kiev", createJourney("Odessa", "Kiev"));
        storage.put("Kiev->Odessa", createJourney("Kiev", "Odessa"));
        storage.put("Lviv->Kiev", createJourney("Lviv", "Kiev"));
    }

    private List<Journey> createJourney(String from, String to){
        return Arrays.asList(
                new Journey(from, to, LocalDate.now(), LocalDate.now().plusDays(1)),
                new Journey(from, to, LocalDate.now().plusDays(1), LocalDate.now().plusDays(2)),
                new Journey(from, to, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3))
        );
    }

    @Override
    public Collection<Journey> find(String stationFrom, String stationTo, LocalDate dateFrom, LocalDate dateTo) {
        if (storage.isEmpty()) return Collections.emptyList();
        String routeKey = stationFrom + "->" + stationTo;
        if (!storage.containsKey(routeKey)){
            return Collections.emptyList();
        }
        List<Journey> out = storage.get(routeKey).stream()
                        .filter(journey -> journey.getDeparture().equals(dateFrom) && journey.getArrival().equals(dateTo))
                        .collect(Collectors.toList());
        return Collections.unmodifiableList(out);
    }
}
