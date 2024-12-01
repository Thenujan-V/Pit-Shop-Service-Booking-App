package com.example.service_mgnt.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class TimeSlotUpdateDto {
    private Date serviceDate;
    private Time serviceStartTime;
    private Time serviceEndTime;
    private Integer maximumNumberOfVehicles;
}
