package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category_price_schedule")
public class CategoryPriceSchedule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_price_id", referencedColumnName = "id")
    private CategoryPrice categoryPrice;

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
