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
@Table(name = "price_categories")
public class PriceCategory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

    @Column(name = "name")
    private String name;

    @Column(name = "priority")
    @Enumerated
    private Priority priority;

    @OneToMany(mappedBy = "priceCategory", cascade = {MERGE, PERSIST, REFRESH}, fetch = FetchType.LAZY)
    private Set<PriceCategorySchedule> priceCategoryScheduleList;

    @Override
    public String toString() {
        return "CategoryPrice{" +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
