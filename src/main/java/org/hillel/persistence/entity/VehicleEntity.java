package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@NamedQueries(value = {
        @NamedQuery(name = "findAllVehicles", query = "select v from VehicleEntity v")
})
@NamedStoredProcedureQueries(value = {
        @NamedStoredProcedureQuery(
                name = "findAll",
                procedureName = "find_all_vehicles",
                parameters = @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Class.class),
                resultClasses = VehicleEntity.class
        )
})
public class VehicleEntity extends AbstractModifyEntity<Long>{

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "vehicle")
    private Set<JourneyEntity> journeys = new HashSet<>();

    @OneToMany(mappedBy = "vehicle", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SeatInfoEntity> seatInfos = new ArrayList<>();

    public void addJourney(final JourneyEntity journey){
        if (journeys == null){
            journeys = new HashSet<>();
        }
        if (journey != null){
            journeys.add(journey);
            journey.setVehicle(this);
        }
    }

    public void addSeatInfo(SeatInfoEntity seatInfo){
        if (seatInfo == null) return;
        if (seatInfos == null) seatInfos = new ArrayList<>();
        seatInfos.add(seatInfo);
        seatInfo.setVehicle(this);
    }

    @Override
    public String toString() {
        return "VehicleEntity{" +
                "name='" + name + '\'' +
                '}';
    }

    public void removeAllJourney(){
        if (CollectionUtils.isEmpty(journeys)) return;
        journeys.forEach(item -> item.setVehicle(null));
    }
}
