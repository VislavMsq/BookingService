package com.project_service.entity;

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
public class ApartmentCategory extends OwnEntity {

    @OneToMany(
            mappedBy = "apartmentCategory",
            cascade = {MERGE, PERSIST, REFRESH},
            fetch = FetchType.LAZY
    )
    private Set<Apartment> apartmentList;

    @OneToMany(
            mappedBy = "apartmentCategory",
            cascade = {MERGE, PERSIST, REFRESH},
            fetch = FetchType.LAZY
    )
    private Set<PriceCategoryToApartmentCategory> categoryToCategorySet;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "type")
    private String type;

    @Column(name = "sleep_place")
    private BigDecimal sleepPlace;

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
