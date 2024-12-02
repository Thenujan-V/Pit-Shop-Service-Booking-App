package com.example.booking.Services.Service;

import com.example.booking.Dto.VehicleDetailsEditDto;
import com.example.booking.Dto.VehicleDto;
import com.example.booking.Entity.VehicleEntity;

import java.util.List;

public interface VehicleService {
    VehicleEntity registerdVehicle(Integer userId, VehicleDto vehicleDto);

    List<VehicleEntity> getUserVehicleDetails(Integer userId);

    VehicleEntity updateVehicleDetails(Integer userId, String vehicleId, VehicleDetailsEditDto vehicleDetailsEditDto);
}
