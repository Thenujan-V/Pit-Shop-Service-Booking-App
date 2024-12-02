package com.example.booking.Controller;

import com.example.booking.Config.Security.TokenProvider;
import com.example.booking.Dto.BookingDto;
import com.example.booking.Entity.BookingEntity;
import com.example.booking.Services.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private TokenProvider tokenProvider;
    private static final String TOKEN_PREFIX = "Bearer";
    @PostMapping("/create-booking")
    private ResponseEntity<?> createBookings(@RequestHeader("Authorization") String token, @Valid @RequestBody BookingDto bookingDto){
        System.out.println("token : "+token);
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing authorization token");
        }

        String authToken = token.replace(TOKEN_PREFIX + " ", "");

        try{
            Integer userId = Math.toIntExact(tokenProvider.getUserIdFromToken(authToken));
            if(userId != null){
                BookingEntity createdBooking = bookingService.bookingCreate(userId, bookingDto);
                return ResponseEntity.ok(createdBooking);
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID could not be retrieved from the token");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
