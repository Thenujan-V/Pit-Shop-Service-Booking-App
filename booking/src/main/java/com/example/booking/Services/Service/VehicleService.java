package com.example.booking.Services.Service;

import com.example.booking.Dto.VehicleDto;
import com.example.booking.Entity.VehicleEntity;

public interface VehicleService {
    VehicleEntity registerdVehicle(Integer userId, VehicleDto vehicleDto);
}
