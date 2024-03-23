package com.project_service.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "price_category_schedule")
public class PriceCategorySchedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_category_id", referencedColumnName = "id")
    private PriceCategory priceCategory;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Override
    public String toString() {
        return "CategoryPriceSchedule{" +
                "endDate=" + endDate +
                ", startDate=" + startDate +
                '}';
    }
}
