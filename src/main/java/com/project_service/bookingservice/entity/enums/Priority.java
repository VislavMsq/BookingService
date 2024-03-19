package com.project_service.bookingservice.entity.enums;

import lombok.Getter;

@Getter
public enum Priority {
    DEFAULT(0),
    LOW(1),
    HIGH(2),
    HOLIDAY(3);

    private final int value;

    Priority(int value) {
        this.value = value;
    }
}
