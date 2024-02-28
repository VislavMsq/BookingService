package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "prices")
public class Price {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "price")
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "is_edited_price")
    private Boolean isEditedPrice;

    @ManyToOne
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;


    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", price=" + price +
                ", date=" + date +
                ", isEditedPrice=" + isEditedPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(id, price1.id) && Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price);
    }
}
