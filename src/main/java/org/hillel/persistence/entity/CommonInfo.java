package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
public class CommonInfo {

    @Column(name = "name")
    private String name;

    @Column(name = "build_date")
    private LocalDate buildDate;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "description")
    private String description;

    public CommonInfo() {
    }
}
