package com.project_service.entity;

import com.project_service.entity.enums.Status;
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
@Table(name = "bookings")
public class Booking extends OwnEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "is_edited_price")
    private Boolean isEditedPrice;

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + getId() +
                ", status=" + status +
                ", price=" + price +
                ", isEditedPrice=" + isEditedPrice +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
