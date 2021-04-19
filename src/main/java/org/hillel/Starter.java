package org.hillel;

import org.hillel.config.RootConfig;
import org.hillel.persistence.entity.*;
import org.hillel.persistence.entity.enums.DirectionType;
import org.hillel.service.TicketClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Instant;
import java.time.LocalDate;

public class Starter {

    public static void main(String[] args) {
        final ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        TicketClient ticketClient = context.getBean(TicketClient.class);

        // Создадим транспортное средство поезд
        VehicleEntity trainVehicle = buildVehicle("Интерсити");
        trainVehicle = ticketClient.createOrUpdateVechicle(trainVehicle);

        // Создадим маршрут с остановками
        JourneyEntity journey = buildJourney("Odessa", "Kiev", Instant.now(), Instant.now().plusSeconds(1000));
        journey.addStop(buildStop("Одесса-Главная", "Одесса", LocalDate.parse("1980-12-03"), "", 1D, 2D));
        journey.addStop(buildStop("Подольск", "Подольск", LocalDate.parse("1980-12-03"), "", 1D, 2D));
        journey.addStop(buildStop("Вапнярка", "Вапнярка", LocalDate.parse("1980-12-03"), "", 5D, 2D));
        journey.addStop(buildStop("Жмеринка", "Жмеринка", LocalDate.parse("1980-12-03"), "", 7D, 2D));
        journey.addStop(buildStop("Винница", "Винница", LocalDate.parse("1980-12-03"), "", 7D, 2D));
        journey.addStop(buildStop("Киев-Пасс.", "Киев", LocalDate.parse("1980-12-03"), "", 1D, 2D));

        // укажем транспорт -> поезд
        journey.setVehicle(trainVehicle);
        journey = ticketClient.createOrUpdateJourney(journey);

        // Укажем кол-во свободных мест для поезда
        trainVehicle.addSeatInfo(buildSeatInfo(journey, trainVehicle, 120));
        trainVehicle = ticketClient.createOrUpdateVechicle(trainVehicle);

        // уменьшим кол-во мест в поезде
        trainVehicle.getSeatInfos().get(0).setFreeSeats(100);
        trainVehicle = ticketClient.createOrUpdateVechicle(trainVehicle);

        //System.out.println(ticketClient.findVehicleByIds(1L,2L,3L));
        System.out.println(ticketClient.findVehicleById(1L, true));
        System.out.println(ticketClient.findAllVehicles());
        //System.out.println(ticketClient.findAllByName("Интерсити"));

        // *********** УДАЛЕНИЕ ************
        // удалим информацию по транспортному средству
//        trainVehicle.getSeatInfos().forEach(ticketClient::removeSeatInfo);

        // удалим само транспортное средство
        trainVehicle.addSeatInfo(buildSeatInfo(ticketClient.findJourneyById(10L, false).get(), trainVehicle, 120));
        trainVehicle.addSeatInfo(buildSeatInfo(ticketClient.findJourneyById(11L, false).get(), trainVehicle, 120));
        trainVehicle.addSeatInfo(buildSeatInfo(ticketClient.findJourneyById(12L, false).get(), trainVehicle, 120));
        trainVehicle = ticketClient.createOrUpdateVechicle(trainVehicle);

        System.out.println("remove vehicle");
        ticketClient.removeVehicle(trainVehicle);
/*

        // удалим одну остановку
        StopEntity stop = journey.getStops().get(0);
        ticketClient.removeStop(stop);

        // удалим весь маршрут
        ticketClient.removeJourneyById(journey.getId());
*/
    }

    private static JourneyEntity buildJourney(final String from, final String to,
                                              final Instant dateFrom, final Instant dateTo){
        final JourneyEntity journey = new JourneyEntity();
        journey.setStationFrom(from);
        journey.setStationTo(to);
        journey.setDateFrom(dateFrom);
        journey.setDateTo(dateTo);
        journey.setDirection(DirectionType.TO);
        journey.setActive(true);
        return journey;
    }

    private static StopEntity buildStop(final String name, final String cityName,
                                        final LocalDate buildDate, final String description,
                                        final Double lat, final Double lon){
        final StopAddInfoEntity stopAddInfo = new StopAddInfoEntity();
        stopAddInfo.setLatitude(lat);
        stopAddInfo.setLongitude(lon);

        final CommonInfo commonInfo = new CommonInfo();
        commonInfo.setName(name);
        commonInfo.setCityName(cityName);
        commonInfo.setBuildDate(buildDate);
        commonInfo.setDescription(description);

        final StopEntity stop = new StopEntity();
        stop.addAddInfo(stopAddInfo);
        stop.setCommonInfo(commonInfo);
        return stop;
    }

    private static VehicleEntity buildVehicle(final String name){
        final VehicleEntity vehicle = new VehicleEntity();
        vehicle.setName(name);
        return vehicle;
    }

    private static SeatInfoEntity buildSeatInfo(final JourneyEntity journey,
            final VehicleEntity vehicle, final Integer freeSeats){
        final SeatInfoEntity seatInfo = new SeatInfoEntity();
        seatInfo.setJourney(journey);
        seatInfo.setVehicle(vehicle);
        seatInfo.setFreeSeats(freeSeats);
        return seatInfo;
    }
}
