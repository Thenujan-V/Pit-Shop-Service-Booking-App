package com.example.booking.Dto;

import com.example.booking.Enum.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    @NotNull(message = "vehicle id is required")
    private String vehicleId;
    @NotNull(message = "booking date is required")
    private Integer timeSlotId;
    @NotNull(message = "status is required")
    private Status status;

    @NotNull(message = "service id date is required")
    private Integer serviceId;
}
