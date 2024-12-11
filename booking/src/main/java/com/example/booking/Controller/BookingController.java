package com.example.booking.Controller;

import com.example.booking.Config.Security.TokenProvider;
import com.example.booking.Dto.BookingDetailsEditDto;
import com.example.booking.Dto.BookingDto;
import com.example.booking.Entity.BookingEntity;
import com.example.booking.Entity.VehicleEntity;
import com.example.booking.Services.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/get-all-booking-details")
    private ResponseEntity<?> getAllBookingDetails(){
        try{
            List<BookingEntity> bookingSlots = bookingService.allBookings();
            return ResponseEntity.ok(bookingSlots);
        }catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @GetMapping("/get-user-booking/{user_id}")
    private ResponseEntity<List<BookingEntity>> getBookingsByUserId(@PathVariable("user_id") Integer userId){
        Optional<List<BookingEntity>> usersBookings = bookingService.getBookingDetails(userId);
        return ResponseEntity.ok(usersBookings.get());
    }

    @PutMapping("/edit-bookings/{booking_id}")
    private ResponseEntity<?> editBookingDetails(@PathVariable("booking_id") Integer bookingId,@RequestBody BookingDetailsEditDto bookingDetailsEditDto){
        try{
            BookingEntity updatedDetails = bookingService.updateBookings(bookingId, bookingDetailsEditDto);
            return ResponseEntity.ok(updatedDetails);
        }catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

}
