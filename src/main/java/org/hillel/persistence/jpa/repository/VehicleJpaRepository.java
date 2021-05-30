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
    @Query(value =
            "select v.* " +
            "from vehicle v " +
            "inner join ( " +
            "    select vehicle_id, dense_rank() over (order by sum(free_seats)) rnk from seat_info group by vehicle_id " +
            "    ) vv on vv.vehicle_id = v.id and vv.rnk = 1",
            nativeQuery = true)
    Collection<VehicleEntity> findWithMinFreeSeats();

    // Метод получения списка транспортных средств с наибольшим количеством свободных мест
    @Query(value =
            "select v.* " +
            "from vehicle v " +
            "inner join ( " +
            "    select vehicle_id, dense_rank() over (order by sum(free_seats) desc) rnk from seat_info group by vehicle_id " +
            "    ) vv on vv.vehicle_id = v.id and vv.rnk = 1",
            nativeQuery = true)
    Collection<VehicleEntity> findWithMaxFreeSeats();
}
