package com.project_service.dto;

import com.project_service.validation.annotation.ValidMonthDay;
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
