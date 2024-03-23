package com.project_service.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "apartment_categories")
public class ApartmentCategory extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "type")
    private String type;

    @Column(name = "sleep_place")
    private BigDecimal sleepPlace;

    @OneToMany(mappedBy = "apartmentCategory", cascade = {MERGE, PERSIST, REFRESH}, fetch = FetchType.LAZY)
    private Set<Apartment> apartmentList;

    @Override
    public String toString() {
        return "ApartmentCategory{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", type='" + type + '\'' +
                ", sleepPlace=" + sleepPlace +
                '}';
    }
}
