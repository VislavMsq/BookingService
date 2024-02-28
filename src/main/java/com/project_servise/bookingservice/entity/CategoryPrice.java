package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.type.descriptor.jdbc.DecimalJdbcType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "category_prices")
public class CategoryPrice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "priority")
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Override
    public String toString() {
        return "CategoryPrice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryPrice that = (CategoryPrice) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(priority, that.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, priority);
    }
}
