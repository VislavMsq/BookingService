package com.project_servise.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "workers_to_apartments")
public class WorkerToApartment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartament_id", referencedColumnName = "id")
    private Apartment apartament;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private User worker;

    @Override
    public String toString() {
        return "WorkerToApartment{" +
                "id=" + getId() +
                '}';
    }
}
