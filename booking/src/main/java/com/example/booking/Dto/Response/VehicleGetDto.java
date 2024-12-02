package com.example.booking.Dto.Response;

import com.example.booking.Enum.FuelType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleGetDto {
    private String vehicleId;
    private Integer userId;
    private String vehicleType;
    private String vehicleBrand;
    private String vehicleModel;
    private String serviceType;
    private FuelType fuelType;
    private Date created_at;
}
