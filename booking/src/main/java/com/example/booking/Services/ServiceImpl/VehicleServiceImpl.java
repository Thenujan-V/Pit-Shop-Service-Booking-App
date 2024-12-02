package com.example.booking.Services.ServiceImpl;

import com.example.booking.Dto.VehicleDto;
import com.example.booking.Entity.BookingEntity;
import com.example.booking.Entity.VehicleEntity;
import com.example.booking.Exception.ConflictException;
import com.example.booking.Repository.VehicleRepository;
import com.example.booking.Services.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Override
    public VehicleEntity registerdVehicle(Integer userId, VehicleDto vehicleDto) {
        if(userId == null){
            throw new IllegalArgumentException("User id cannot be null or empty");
        }

        VehicleEntity vehicleEntity = new VehicleEntity();
        try{
            vehicleEntity.setUserId(userId);
            vehicleEntity.setVehicleId(vehicleDto.getVehicleId());
            vehicleEntity.setVehicleBrand(vehicleDto.getVehicleBrand());
            vehicleEntity.setVehicleModel(vehicleDto.getVehicleModel());
            vehicleEntity.setVehicleType(vehicleDto.getVehicleType());
            vehicleEntity.setServiceType(vehicleDto.getServiceType());
            vehicleEntity.setFuelType(vehicleDto.getFuelType());

            return vehicleRepository.save(vehicleEntity);

        }catch(Exception e){
            throw new ConflictException("Database constraint violation", e);
        }
    }
}
