package org.hillel;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.CommonInfo;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopAddInfoEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.enums.DirectionType;
import org.hillel.service.TicketClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;

public class Starter {

    public static void main(String[] args) throws Exception {
        final ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        //final ApplicationContext context = new ClassPathXmlApplicationContext("common-beans.xml");
        System.out.println("after init");

        TicketClient ticketClient = context.getBean(TicketClient.class);
        Collection<Journey> journeys = ticketClient.find("Kiev", "Odessa",
                LocalDate.parse("2021-06-20"), LocalDate.parse("2021-07-21"));
        journeys.forEach(System.out::println);

        JourneyEntity journeyEntity = new JourneyEntity();
        journeyEntity.setStationFrom("Odessa");
        journeyEntity.setStationTo("Kiev");
        journeyEntity.setDateFrom(Instant.now());
        journeyEntity.setDateTo(Instant.now());
        journeyEntity.setDirection(DirectionType.UNKNOWN);
        journeyEntity.setActive(false);
        ticketClient.createJourney(journeyEntity);

        StopAddInfoEntity stopAddInfoEntity = new StopAddInfoEntity();
        stopAddInfoEntity.setLatitude(11.0);
        stopAddInfoEntity.setLongitude(1.3);
        StopEntity stopEntity = new StopEntity();
        //stopEntity.addAddInfo(stopAddInfoEntity);
        stopEntity.setCommonInfo(new CommonInfo());
        ticketClient.createStop(stopEntity);

        System.out.println("create journey with id " + journeyEntity.getId());
    }
}
