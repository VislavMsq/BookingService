package com.project_service.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "price_category_schedule")
public class CategoryPriceSchedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_category_id", referencedColumnName = "id")
    private PriceCategory priceCategory;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Override
    public String toString() {
        return "CategoryPriceSchedule{" +
                "endDate=" + endDate +
                ", startDate=" + startDate +
                '}';
    }
}
