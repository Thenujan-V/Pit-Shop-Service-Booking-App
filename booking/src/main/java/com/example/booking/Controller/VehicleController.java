package com.example.booking.Controller;

import com.example.booking.Config.Security.TokenProvider;
import com.example.booking.Dto.Response.VehicleGetDto;
import com.example.booking.Dto.VehicleDetailsEditDto;
import com.example.booking.Dto.VehicleDto;
import com.example.booking.Entity.BookingEntity;
import com.example.booking.Entity.VehicleEntity;
import com.example.booking.Repository.VehicleRepository;
import com.example.booking.Services.Service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private TokenProvider tokenProvider;
    private static final String TOKEN_PREFIX = "Bearer";

    @PostMapping("/register-vehicle")
    private ResponseEntity<?> registerVehicles(@RequestHeader("Authorization") String token, @Valid @RequestBody VehicleDto vehicleDto){
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing authorization token");
        }

        String authToken = token.replace(TOKEN_PREFIX + " ", "");

        try{
            Integer userId = Math.toIntExact(tokenProvider.getUserIdFromToken(authToken));
            if(userId != null){
                VehicleEntity registedVehicle = vehicleService.registerdVehicle(userId, vehicleDto);
                return ResponseEntity.ok(registedVehicle);
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID could not be retrieved from the token");
            }
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
    @GetMapping("/get-vehicle-details/{userId}")
    private ResponseEntity<?> getVehicleDetails(@PathVariable("userId") Integer userId){
        try{
            List<VehicleEntity> userVehicleDetails = vehicleService.getUserVehicleDetails(userId);
            return ResponseEntity.ok(userVehicleDetails);
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PutMapping("/edit-vehicle-details/{vehicle_id}")
    private ResponseEntity<?> editVehicleDetails(@RequestHeader("Authorization") String token, @PathVariable("vehicle_id") String vehicleId,@RequestBody VehicleDetailsEditDto vehicleDetailsEditDto){
        if(token == null || !token.startsWith(TOKEN_PREFIX)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing authorization token");
        }
        String authToken = token.replace(TOKEN_PREFIX + " ", "");

        try{
            Integer userId = Math.toIntExact(tokenProvider.getUserIdFromToken(authToken));

            if(userId != null){
                VehicleEntity updatedEntity = vehicleService.updateVehicleDetails(userId, vehicleId, vehicleDetailsEditDto);
                return ResponseEntity.ok(updatedEntity);
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID could not be retrieved from the token");
            }
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping("/get-all-vehicles")
    private ResponseEntity<?> getAllVehicleDetails(){
        try{
            List<VehicleEntity> allVehicles = vehicleService.getVehicledetails();
            return ResponseEntity.ok(allVehicles);
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @DeleteMapping("/delete-vehicle/{vehicle_id}")
    private ResponseEntity<?> deleteVehicles(@RequestHeader("Authorization") String token, @PathVariable("vehicle_id") String vehicle_id){
        try{
            ResponseEntity<?> deletedVehicle = vehicleService.deleteVehicleDetails(vehicle_id);
            return ResponseEntity.ok(deletedVehicle);
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}























