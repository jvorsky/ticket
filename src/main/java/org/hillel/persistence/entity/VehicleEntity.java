package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
public class VehicleEntity extends AbstractModifyEntity<Long>{

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER)
    private Set<JourneyEntity> journeys = new HashSet<>();

    public void addJourney(final JourneyEntity journey){
        if (journeys == null){
            journeys = new HashSet<>();
        }
        if (journey != null){
            journeys.add(journey);
            journey.addVehicle(this);
        }
    }

    @Override
    public String toString() {
        return "VehicleEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
