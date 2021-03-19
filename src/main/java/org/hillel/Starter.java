package org.hillel;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.service.TicketClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        journeyEntity.setDateFrom(LocalDate.parse("2021-06-20"));
        journeyEntity.setDateTo(LocalDate.parse("2021-06-21"));
        ticketClient.createJourney(journeyEntity);

        System.out.println("create journey with id " + journeyEntity.getId());
    }
}
