package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.BitSet;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "is_edited_price")
    private Boolean isEditedPrice;

    @Column(name = "currency_code")
    private String currencyCode;

    @UpdateTimestamp
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @CreationTimestamp
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "apartament_id", referencedColumnName = "id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", isEditedPrice=" + isEditedPrice +
                ", currencyCode='" + currencyCode + '\'' +
                ", createdAt=" + createdAt +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(status, booking.status) && Objects.equals(price, booking.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, price);
    }
}
