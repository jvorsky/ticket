package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hillel.persistence.entity.enums.DirectionType;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "journey")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
public class JourneyEntity extends AbstractModifyEntity<Long> {

    @Column(name = "station_from", length = 50, nullable = false, columnDefinition = "varchar(100) default 'NONE'")
    private String stationFrom;

    @Column(name = "station_to", length = 50, nullable = false, columnDefinition = "varchar(100) default 'NONE'")
    private String stationTo;

    @Column(name = "date_from", nullable = false)
    private Instant dateFrom;

    @Column(name = "date_to", nullable = false)
    private Instant dateTo;

    @Column(name = "direction", length = 20)
    @Enumerated(EnumType.STRING)
    private DirectionType direction = DirectionType.TO;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    public void addVehicle(final VehicleEntity vehicle){
        this.vehicle = vehicle;
    }

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "journey_stop", indexes = @Index(name = "journey_stop_idx", columnList = "journey_id, stop_id"),
            joinColumns = @JoinColumn(name = "journey_id"),
            inverseJoinColumns = @JoinColumn(name = "stop_id"))
    private List<StopEntity> stops = new ArrayList<>();

    public void addStop(final StopEntity stop){
        if (stop == null) return;
        if (stops == null) stops = new ArrayList<>();
        stops.add(stop);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JourneyEntity)) return false;
        JourneyEntity journey = (JourneyEntity) o;
        return getId() != null && Objects.equals(journey.getId(), getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "JourneyEntity{" +
                "stationFrom='" + stationFrom + '\'' +
                ", stationTo='" + stationTo + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", direction=" + direction +
                ", vehicle=" + vehicle +
                '}';
    }
}
