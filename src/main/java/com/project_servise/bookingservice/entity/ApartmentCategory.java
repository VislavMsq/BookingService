package com.project_servise.bookingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
