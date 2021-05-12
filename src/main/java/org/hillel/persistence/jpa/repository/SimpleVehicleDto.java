package org.hillel.persistence.jpa.repository;

public interface SimpleVehicleDto {

    Long getId();
    String getName();
    boolean isActive();

    default void toStr(){
        System.out.println("id: " + getId() + " name: " + getName() + " active: " + isActive());
    }
}
