package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "apartments")
public class Apartment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    private Apartment parent;

    @ManyToOne
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "apartment_category_id",referencedColumnName = "id")
    private ApartmentCategory apartmentCategoryId;

    @ManyToOne
    @JoinColumn(name = "currency_id",referencedColumnName = "id")
    private Currency currency;

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
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
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return Objects.equals(id, apartment.id) && Objects.equals(name, apartment.name) && Objects.equals(type, apartment.type) && Objects.equals(country, apartment.country) && Objects.equals(city, apartment.city) && Objects.equals(street, apartment.street) && Objects.equals(floor, apartment.floor) && Objects.equals(pet, apartment.pet) && Objects.equals(smokable, apartment.smokable) && Objects.equals(parkingPlace, apartment.parkingPlace) && Objects.equals(description, apartment.description) && Objects.equals(createdAt, apartment.createdAt) && Objects.equals(parent, apartment.parent) && Objects.equals(owner, apartment.owner) && Objects.equals(apartmentCategoryId, apartment.apartmentCategoryId) && Objects.equals(currency, apartment.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, city);
    }
}
