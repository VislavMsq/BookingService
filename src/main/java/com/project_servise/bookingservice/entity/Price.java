package com.project_servise.bookingservice.entity;

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
@Table(name = "prices")
public class Price extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Column(name = "price")
    private BigDecimal pricePerDay;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "is_edited_price")
    private Boolean isEditedPrice;

    @Override
    public String toString() {
        return "Price{" +
                "pricePerDay=" + pricePerDay +
                ", date=" + date +
                ", isEditedPrice=" + isEditedPrice +
                '}';
    }
}