package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface VehicleJpaRepository extends
        CommonJpaRepository<VehicleEntity, Long>, JpaSpecificationExecutor<VehicleEntity> {

    @Modifying
    @Query("delete from SeatInfoEntity where vehicle = :vehicleParam")
    void deleteSeatInfoByVehicle(@Param("vehicleParam") VehicleEntity vehicle);

    @Query(value = "select v from VehicleEntity v where id between :idFrom and :idTo and v.name = :name",
            countQuery = "select v.id from VehicleEntity v")
    Page<VehicleEntity> findFirstByConditions(
            @Param("name") String name,
            @Param("idFrom") Long idFrom,
            @Param("idTo") Long idTo,
            Pageable page);

    List<SimpleVehicleDto> findAllByActiveIsTrue();

    // Метод получения списка транспортных средств с наименьшим количеством свободных мест
    @Query(value = "select vehicle.* from vehicle \n" +
            "       inner join seat_info on vehicle.id = seat_info.vehicle_id \n" +
            "       group by vehicle.id \n" +
            "       having sum(free_seats) = \n" +
            "              (select min(sumFreeSeats) \n" +
            "               from (select sum(free_seats) sumFreeSeats \n" +
            "                     from seat_info \n" +
            "                     group by vehicle_id) seat \n" +
            "              )", nativeQuery = true)
    Collection<VehicleEntity> findWithMinFreeSeats();

    // Метод получения списка транспортных средств с наибольшим количеством свободных мест
    @Query(value = "select vehicle.* from vehicle \n" +
            "       inner join seat_info on vehicle.id = seat_info.vehicle_id \n" +
            "       group by vehicle.id \n" +
            "       having sum(free_seats) = \n" +
            "              (select max(sumFreeSeats) \n" +
            "               from (select sum(free_seats) sumFreeSeats \n" +
            "                     from seat_info \n" +
            "                     group by vehicle_id) seat \n" +
            "              )", nativeQuery = true)
    Collection<VehicleEntity> findWithMaxFreeSeats();
}
