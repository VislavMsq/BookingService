package com.project_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "workers_to_apartments")
public class WorkerToApartment extends OwnEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY)
    private User worker;

    @Override
    public String toString() {
        return "WorkerToApartment{" +
                "id=" + getId() +
                '}';
    }
}
