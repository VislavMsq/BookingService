package com.project_service.bookingservice.dto;

import com.project_service.bookingservice.validation.annotation.ValidMonthDay;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ScheduleDto {

    @Pattern(regexp = "\\d{1,2}-\\d{1,2}", message = "Invalid date format, use MM-dd")
    @ValidMonthDay
    private String startDate;
    @Pattern(regexp = "\\d{1,2}-\\d{1,2}", message = "Invalid date format, use MM-dd")
    @ValidMonthDay
    private String endDate;
}
