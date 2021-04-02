package org.hillel.config;

import org.hillel.service.InMemoryJourneyServiceImpl;
import org.hillel.service.JourneyService;
import org.hillel.service.StubJourneyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"org.hillel.config", "org.hillel.service", "org.hillel.persistence"})
@PropertySource({"application.properties"})
public class RootConfig {

//    @Bean
//    public TicketClient getTicketClient() throws Exception {
//        return new TicketClient();
//    }

    @Bean("inMemoryJourneyService")
    public JourneyService getMemoryJourneyService(){
        return new InMemoryJourneyServiceImpl();
    }

    @Bean("stubJourneyService")
    public JourneyService stubJourneyService(){
        return new StubJourneyServiceImpl();
    }

}
