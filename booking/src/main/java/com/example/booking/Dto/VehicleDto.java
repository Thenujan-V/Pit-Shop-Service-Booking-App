package com.example.booking.Dto;

import com.example.booking.Enum.FuelType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {
    @NotNull(message = "vehicle id is required")
    private String vehicleId;
    @NotNull(message = "vehicle type is required")
    private String vehicleType;
    @NotNull(message = "vehicle brand is required")
    private String vehicleBrand;
    @NotNull(message = "vehicle model is required")
    private String vehicleModel;
    @NotNull(message = "service type is required")
    private String serviceType;
    @NotNull(message = "fuel type is required")
    private FuelType fuelType;

}
