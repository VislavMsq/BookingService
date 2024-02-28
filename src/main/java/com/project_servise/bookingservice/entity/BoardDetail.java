package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board_details")
public class BoardDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "price")
    private BigDecimal price;

    @UpdateTimestamp
    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "apartments_city")
    private String apartmentCity;

    @Column(name = "apartments_sleeping_places")
    private BigDecimal apartmentSleepingPlace;

    @ManyToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currnecy;

    @Override
    public String toString() {
        return "BoardDetail{" +
                "id=" + id +
                ", price=" + price +
                ", date=" + date +
                ", clientName='" + clientName + '\'' +
                ", apartmentCity='" + apartmentCity + '\'' +
                ", apartmentSleepingPlace=" + apartmentSleepingPlace +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardDetail that = (BoardDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(price, that.price) && Objects.equals(clientName, that.clientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, clientName);
    }
}
