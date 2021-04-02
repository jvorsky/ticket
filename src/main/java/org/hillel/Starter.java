package org.hillel;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.*;
import org.hillel.persistence.entity.enums.DirectionType;
import org.hillel.service.TicketClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Instant;

public class Starter {

    public static void main(String[] args) throws Exception {
        final ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        //final ApplicationContext context = new ClassPathXmlApplicationContext("common-beans.xml");
        System.out.println("after init");

        TicketClient ticketClient = context.getBean(TicketClient.class);

        JourneyEntity journeyEntity = new JourneyEntity();
        journeyEntity.setStationFrom("Odessa");
        journeyEntity.setStationTo("Kiev");
        journeyEntity.setDateFrom(Instant.now());
        journeyEntity.setDateTo(Instant.now());
        journeyEntity.setDirection(DirectionType.UNKNOWN);
        journeyEntity.setActive(false);

        final VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setName("bus1");
        journeyEntity.addVehicle(vehicleEntity);

        StopAddInfoEntity stopAddInfoEntity = new StopAddInfoEntity();
        stopAddInfoEntity.setLatitude(11.0);
        stopAddInfoEntity.setLongitude(1.3);

        StopEntity stopEntity = new StopEntity();
        stopEntity.addAddInfo(stopAddInfoEntity);
        stopEntity.setCommonInfo(new CommonInfo());
        journeyEntity.addStop(stopEntity);

        ticketClient.createJourney(journeyEntity);
        System.out.println("create journey with id " + journeyEntity.getId());

        final JourneyEntity journey = ticketClient.getJourneyById(journeyEntity.getId(), true).get();
        System.out.println("find journey by id " + journey);
        System.out.println("get all stops by journey " + journey.getStops());

        journey.setDirection(DirectionType.TO);
        System.out.println("save journey");
        ticketClient.saveJourney(journey);
    }
}
