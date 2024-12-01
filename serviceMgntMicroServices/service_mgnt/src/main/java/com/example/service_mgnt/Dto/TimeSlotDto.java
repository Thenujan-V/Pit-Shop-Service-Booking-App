package com.example.service_mgnt.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class TimeSlotDto {
    @NotNull(message = "manager_id is required")
    private Integer managerId;
    @NotNull(message = "serviceDate is required")
    private Date serviceDate;
    @NotNull(message = "serviceStartTime is required")
    private Time serviceStartTime;
    @NotNull(message = "serviceEndTime is required")
    private Time serviceEndTime;
    @NotNull(message = "maximumNumberOfVehicles is required")
    private Integer maximumNumberOfVehicles;
}
