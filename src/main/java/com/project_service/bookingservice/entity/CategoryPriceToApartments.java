package com.project_service.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Year;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "price_category_to_apartments_categories")
public class CategoryPriceToApartments extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_category_id", referencedColumnName = "id")
    private ApartmentCategory apartmentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_category_id", referencedColumnName = "id")
    private PriceCategory priceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "period")
    private Year period;

    @Override
    public String toString() {
        return "CategoryPriceToApartments{" +
                "id=" + getId() +
                ", price=" + price +
                ", period=" + period +
                '}';
    }
}
