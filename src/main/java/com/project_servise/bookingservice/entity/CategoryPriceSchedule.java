package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category_price_schedule")
public class CategoryPriceSchedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_price_id", referencedColumnName = "id")
    private CategoryPrice categoryPrice;

    @Override
    public String toString() {
        return "CategoryPriceSchedule{" +
                "id=" + getId() +
                '}';
    }
}
