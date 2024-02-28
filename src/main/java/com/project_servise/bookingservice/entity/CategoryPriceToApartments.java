package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category_price_to_apartments")
public class CategoryPriceToApartments {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "price")
    private BigDecimal price;

    @UpdateTimestamp
    @Column(name = "year")
    private LocalDateTime year;

    @ManyToOne
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "category_price_id", referencedColumnName = "id")
    private CategoryPrice categoryPrice;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Override
    public String toString() {
        return "CategoryPriceToApartments{" +
                "id=" + id +
                ", price=" + price +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryPriceToApartments that = (CategoryPriceToApartments) o;
        return Objects.equals(id, that.id) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price);
    }
}
