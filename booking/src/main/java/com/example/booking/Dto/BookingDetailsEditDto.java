package com.example.booking.Dto;

import com.example.booking.Enum.Status;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsEditDto {
    private String vehicleId;
    private Integer timeSlotId;

    private Status status;
}
