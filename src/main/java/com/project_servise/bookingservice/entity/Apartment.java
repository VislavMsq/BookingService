package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "apartments")
public class Apartment extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "pet")
    private Boolean pet;

    @Column(name = "smokable")
    private Boolean smokable;

    @Column(name = "parking_place")
    private Integer parkingPlace;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Apartment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_category_id", referencedColumnName = "id")
    private ApartmentCategory apartmentCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", floor=" + floor +
                ", pet=" + pet +
                ", smokable=" + smokable +
                ", parkingPlace=" + parkingPlace +
                ", description='" + description + '\'' +
                '}';
    }
}
