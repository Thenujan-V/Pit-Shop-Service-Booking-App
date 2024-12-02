package com.example.booking.Controller;

import com.example.booking.Config.Security.TokenProvider;
import com.example.booking.Dto.VehicleDto;
import com.example.booking.Entity.BookingEntity;
import com.example.booking.Entity.VehicleEntity;
import com.example.booking.Repository.VehicleRepository;
import com.example.booking.Services.Service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
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
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
