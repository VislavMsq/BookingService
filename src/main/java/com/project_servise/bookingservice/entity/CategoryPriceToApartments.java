package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "price_category_to_apartments_categories")
public class CategoryPriceToApartments extends BaseEntity {

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "year")
    private LocalDateTime year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_category_id", referencedColumnName = "id")
    private ApartmentCategory apartmentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_category_id", referencedColumnName = "id")
    private PriceCategory priceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Override
    public String toString() {
        return "CategoryPriceToApartments{" +
                "id=" + getId() +
                ", price=" + price +
                ", year=" + year +
                '}';
    }
}
