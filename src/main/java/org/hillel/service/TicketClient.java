package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.SeatInfoEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.service.old.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Collection;
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
    private TransactionalSeatInfoService seatInfoService;

    @Autowired
    private TransactionalVehicleService vehicleService;

    @Autowired
    private Environment environment;

    @Value("${datasource.url}")
    private String url;

    public TicketClient(){
    }

    public JourneyEntity createOrUpdateJourney(JourneyEntity journey) {
        return journeyService.createOrUpdate(journey);
    }

    public Optional<JourneyEntity> findJourneyById(Long id, boolean withDependencies){
        return id == null ? Optional.empty() : journeyService.findById(id, withDependencies);
    }

    public StopEntity createOrUpdateStop(StopEntity stopEntity){
        return stopService.createOrUpdate(stopEntity);
    }

    public SeatInfoEntity createOrUpdateSeatInfo(SeatInfoEntity seatInfo) {
        return seatInfoService.createOrUpdate(seatInfo);
    }

    public VehicleEntity createOrUpdateVechicle(VehicleEntity vehicle) {
        return vehicleService.createOrUpdate(vehicle);
    }

    public void removeJourney(JourneyEntity journey) {
        journeyService.remove(journey);
    }

    public void removeJourneyById(Long journeyId) {
        journeyService.removeById(journeyId);
    }

    public void removeVehicle(VehicleEntity vehicleEntity){
        vehicleService.remove(vehicleEntity);
    }

    public void removeVehicleById(Long id){
        vehicleService.removeById(id);
    }

    public void removeSeatInfo(SeatInfoEntity seatInfo){
        seatInfoService.remove(seatInfo);
    }

    public void removeSeatInfoById(Long id){
        seatInfoService.removeById(id);
    }

    public void removeStop(StopEntity stop) {
        stopService.remove(stop);
    }

    public Collection<VehicleEntity> findVehicleByIds(Long... ids){
        return vehicleService.findByIds(ids);
    }

    public VehicleEntity findVehicleById(Long id, boolean withDep){
        return vehicleService.findById(id, withDep).get();
    }

    public Collection<VehicleEntity> findAllByName(String name){
        return vehicleService.findAllByName(name);
    }

    public Collection<JourneyEntity> findAllJourneys(QueryType queryType,
                                                     int pageNumber,
                                                     int pageSize,
                                                     String orderFieldName,
                                                     boolean orderAsc){
        QueryContext queryContext = new QueryContext(queryType, pageNumber, pageSize, orderFieldName, orderAsc);
        return journeyService.findAll(queryContext);
    }
    public Collection<SeatInfoEntity> findAllSeatInfos(QueryType queryType,
                                                       int pageNumber,
                                                       int pageSize,
                                                       String orderFieldName,
                                                       boolean orderAsc){
        QueryContext queryContext = new QueryContext(queryType, pageNumber, pageSize, orderFieldName, orderAsc);
        return seatInfoService.findAll(queryContext);
    }
    public Collection<StopEntity> findAllStops(QueryType queryType,
                                               int pageNumber,
                                               int pageSize,
                                               String orderFieldName,
                                               boolean orderAsc){
        QueryContext queryContext = new QueryContext(queryType, pageNumber, pageSize, orderFieldName, orderAsc);
        return stopService.findAll(queryContext);
    }

    public Collection<VehicleEntity> findAllVehicles(QueryType queryType,
                                                     int pageNumber,
                                                     int pageSize,
                                                     String orderFieldName,
                                                     boolean orderAsc){
        QueryContext queryContext = new QueryContext(queryType, pageNumber, pageSize, orderFieldName, orderAsc);
        return vehicleService.findAll(queryContext);
    }

    public Collection<VehicleEntity> findVehicleWithMinFreeSeats(){
        return vehicleService.findWithMinFreeSeats();
    }

    public Collection<VehicleEntity> findVehicleWithMaxFreeSeats(){
        return vehicleService.findWithMaxFreeSeats();
    }
}
