package com.example.booking.Services.ServiceImpl;

import com.example.booking.Dto.VehicleDetailsEditDto;
import com.example.booking.Dto.VehicleDto;
import com.example.booking.Entity.VehicleEntity;
import com.example.booking.Exception.ConflictException;
import com.example.booking.Repository.VehicleRepository;
import com.example.booking.Services.Service.VehicleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
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

    @Override
    public List<VehicleEntity> getUserVehicleDetails(Integer userId) {
        try{
            System.out.println("user id 2 : "+ vehicleRepository.findVehicleDetails(userId));
            List<VehicleEntity> vehicleDetails = vehicleRepository.findVehicleDetails(userId);

            if(vehicleDetails != null){
                return vehicleDetails;
            }
            else{
                throw new ResponseStatusException(
                        HttpStatus.NO_CONTENT,
                        "No Details. Please try again later."
                );
            }
        }catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to fetch vehicle details at the moment. Please try again later."
            );
        }
    }

    @Override
    public VehicleEntity updateVehicleDetails(Integer userId, String vehicleId, VehicleDetailsEditDto vehicleDetailsEditDto) {
        if(userId == null){
            throw new IllegalArgumentException("user id cannot be null");
        }
        if(vehicleId == null){
            throw new IllegalArgumentException("vehicle id cannot be null");
        }
        System.out.println("userId: "+userId);
        System.out.println("vehicle_id: "+vehicleId);

        try{
            System.out.println("Test7");

            Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(vehicleId.trim());
//            Optional<VehicleEntity> existingVehicle = vehicleRepository.findDetails(vehicleNo);
            System.out.println("evvvv: "+existingVehicle);

            if(existingVehicle.isPresent()){
                VehicleEntity vehicleEntity = new VehicleEntity();
                if(vehicleDetailsEditDto.getVehicleBrand() != null){
                    vehicleEntity.setVehicleBrand(vehicleDetailsEditDto.getVehicleBrand());
                }
                if(vehicleDetailsEditDto.getVehicleType() != null){
                    vehicleEntity.setVehicleType(vehicleDetailsEditDto.getVehicleType());
                }
                if(vehicleDetailsEditDto.getVehicleModel() != null){
                    vehicleEntity.setVehicleModel(vehicleDetailsEditDto.getVehicleModel());
                }
                if(vehicleDetailsEditDto.getFuelType() != null){
                    vehicleEntity.setFuelType(vehicleDetailsEditDto.getFuelType());
                }
                if(vehicleDetailsEditDto.getServiceType() != null){
                    vehicleEntity.setServiceType(vehicleDetailsEditDto.getServiceType());
                }
                System.out.println("test 1");
                VehicleEntity updatedVehicleDetails = vehicleRepository.save(vehicleEntity);
                System.out.println("test 2");
                return updatedVehicleDetails;
            }
            else{
                System.out.println("else: ");

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error 1");
            }
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error2");
        }
    }
}





































































