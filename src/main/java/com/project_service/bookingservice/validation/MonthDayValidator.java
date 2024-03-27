package com.project_service.bookingservice.validation;

import com.project_service.bookingservice.validation.annotation.ValidMonthDay;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

public class MonthDayValidator implements ConstraintValidator<ValidMonthDay, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            MonthDay.parse(value, DateTimeFormatter.ofPattern("M-d"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
