package com.project_service.bookingservice.validation.annotation;

import com.project_service.bookingservice.validation.MonthDayValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MonthDayValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMonthDay {

    String message() default "Invalid date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
