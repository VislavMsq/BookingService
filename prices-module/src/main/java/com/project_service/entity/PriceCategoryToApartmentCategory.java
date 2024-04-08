package com.project_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "price_category_to_apartments_categories")
public class PriceCategoryToApartmentCategory extends OwnEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private ApartmentCategory apartmentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    private PriceCategory priceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "period")
    private Integer period;

    @Override
    public String toString() {
        return "CategoryPriceToApartments{" +
                "id=" + getId() +
                ", price=" + price +
                ", period=" + period +
                '}';
    }
}
