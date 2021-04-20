package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stop")
@Getter
@Setter
@NoArgsConstructor
@NamedQueries(value = {
        @NamedQuery(name = "findAllStops", query = "select v from StopEntity v")
})
public class StopEntity extends AbstractModifyEntity<Long> {

    @Embedded
    private CommonInfo commonInfo;

    @OneToOne(mappedBy = "stop", cascade = {CascadeType.PERSIST})
    private StopAddInfoEntity stopAddInfo;

    @ManyToMany(mappedBy = "stops")
    private List<JourneyEntity> journeys = new ArrayList<>();

    public void addAddInfo(StopAddInfoEntity stopAddInfo){
        if (stopAddInfo == null){
            this.stopAddInfo = null;
            return;
        }
        stopAddInfo.setStop(this);
        this.setStopAddInfo(stopAddInfo);
    }

    public void addJourney(JourneyEntity journey){
        if (journey == null) return;
        if (journeys == null) journeys = new ArrayList<>();
        journeys.add(journey);
    }

    public void removeAllJourneys(){
        if (CollectionUtils.isEmpty(journeys)) return;
        journeys.forEach(journeyEntity -> journeyEntity.getStops().remove(this));
        this.journeys.clear();
    }

    @Override
    public String toString() {
        return "StopEntity{" +
                "name=" + commonInfo.getName() +
                '}';
    }
}
