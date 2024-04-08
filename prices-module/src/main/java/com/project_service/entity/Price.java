package com.project_service.entity;

import com.project_service.entity.enums.Priority;
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
public class Price extends OwnEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;

    @Column(name = "price")
    private BigDecimal pricePerDay;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "is_edited_price")
    private Boolean isEditedPrice;

    @Enumerated
    @Column(name = "priority")
    private Priority priority;

    @Override
    public String toString() {
        return "Price{" +
                "pricePerDay=" + pricePerDay +
                ", date=" + date +
                ", isEditedPrice=" + isEditedPrice +
                ", priority=" + priority +
                '}';
    }
}
