package com.project_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board_details")
public class BoardDetail extends OwnEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "apartments_city")
    private String apartmentCity;

    @Column(name = "apartments_sleeping_places")
    private BigDecimal apartmentSleepingPlace;

    @Override
    public String toString() {
        return "BoardDetail{" +
                "id=" + getId() +
                ", price=" + price +
                ", date=" + date +
                ", clientName='" + clientName + '\'' +
                ", apartmentCity='" + apartmentCity + '\'' +
                ", apartmentSleepingPlace=" + apartmentSleepingPlace +
                '}';
    }
}
