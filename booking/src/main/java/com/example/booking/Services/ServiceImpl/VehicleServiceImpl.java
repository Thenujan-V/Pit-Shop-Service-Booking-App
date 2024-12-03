package com.example.booking.Services.ServiceImpl;

import com.example.booking.Dto.VehicleDetailsEditDto;
import com.example.booking.Dto.VehicleDto;
import com.example.booking.Entity.VehicleEntity;
import com.example.booking.Enum.FuelType;
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

            String fuelTypeString = String.valueOf(vehicleDto.getFuelType());
            FuelType fuelType = FuelType.valueOf(fuelTypeString.toLowerCase());
            vehicleEntity.setFuelType(fuelType);

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


        try{
            Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(vehicleId.trim());
            if(existingVehicle.isPresent()){
                VehicleEntity vehicleEntity = existingVehicle.get();

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
                VehicleEntity updatedVehicleDetails = vehicleRepository.save(vehicleEntity);
                return updatedVehicleDetails;
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<VehicleEntity> getVehicledetails() {
        try{
            List<VehicleEntity> allVehiclesDetails = vehicleRepository.findAll();
            return allVehiclesDetails;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"database exception");
        }
    }

    @Override
    public ResponseEntity<?> deleteVehicleDetails(String vehicleId) {
        try{
            Optional<VehicleEntity> existingVehicle = vehicleRepository.findById(vehicleId);
            if(existingVehicle.isPresent()){
                VehicleEntity deletedVehicle = existingVehicle.get();
                deletedVehicle.setVehicleIsActive(false);
                vehicleRepository.save(deletedVehicle);
                return ResponseEntity.ok("Vehicle deleted successfully.");
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "vehicle not found.");
            }
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}





































































