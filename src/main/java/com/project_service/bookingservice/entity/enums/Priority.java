package com.project_service.bookingservice.entity.enums;

import lombok.Getter;

@Getter
public enum Priority {
    DEFAULT(0),
    LOW(1),
    HIGH(2),
    HOLIDAY(3);

    private final Integer value;

    Priority(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
