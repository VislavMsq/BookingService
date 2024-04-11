package com.project_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "price_category_schedule")
public class PriceCategorySchedule {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_category_id")
    private PriceCategory priceCategory;

    @Column(name = "start_month")
    private Integer startMonth;

    @Column(name = "start_Day")
    private Integer startDay;

    @Column(name = "end_month")
    private Integer endMonth;

    @Column(name = "end_day")
    private Integer endDay;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "PriceCategorySchedule{" +
                "startMonth=" + startMonth +
                ", startDay=" + startDay +
                ", endMonth=" + endMonth +
                ", endDay=" + endDay +
                '}';
    }
}
