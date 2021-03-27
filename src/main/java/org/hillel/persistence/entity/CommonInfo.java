package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class CommonInfo {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public CommonInfo() {
    }
}
