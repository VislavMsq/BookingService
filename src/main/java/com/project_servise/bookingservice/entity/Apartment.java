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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Apartment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_category_id", referencedColumnName = "id")
    private ApartmentCategory apartmentCategoryId;

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

    @Column(name = "smoking")
    private Boolean smoking;

    @Column(name = "parking_place")
    private Integer parkingPlace;

    @Column(name = "description")
    private String description;

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
                ", smoking=" + smoking +
                ", parkingPlace=" + parkingPlace +
                ", description='" + description + '\'' +
                '}';
    }
}
