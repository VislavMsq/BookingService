package com.project_service.bookingservice.entity;

import com.project_service.bookingservice.entity.enums.Priority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedEntityGraph(
        name = "priceCategory.priceCategoryScheduleList",
        attributeNodes = {
                @NamedAttributeNode("priceCategoryScheduleSet"),
                @NamedAttributeNode("currency")})
@Table(name = "price_categories")
public class PriceCategory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @OneToMany(
            mappedBy = "priceCategory",
            cascade = {MERGE, REMOVE, PERSIST, REFRESH},
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<PriceCategorySchedule> priceCategoryScheduleSet;

    @OneToMany(
            mappedBy = "priceCategory",
            cascade = {MERGE, PERSIST, REFRESH},
            fetch = FetchType.LAZY
    )
    private Set<PriceCategoryToApartmentCategory> categoryToCategorySet;

    @Column(name = "name")
    private String name;

    @Enumerated
    @Column(name = "priority")
    private Priority priority;

    @Override
    public String toString() {
        return "CategoryPrice{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
