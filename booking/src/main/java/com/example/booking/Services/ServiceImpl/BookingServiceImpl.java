package com.example.booking.Services.ServiceImpl;

import com.example.booking.Dto.BookingDetailsEditDto;
import com.example.booking.Dto.BookingDto;
import com.example.booking.Entity.BookingEntity;
import com.example.booking.Enum.Status;
import com.example.booking.Exception.BookingAlreadyExistsException;
import com.example.booking.Exception.ConflictException;
import com.example.booking.Exception.DatabaseException;
import com.example.booking.Exception.NoContentException;
import com.example.booking.Repository.BookingRepository;
import com.example.booking.Services.Service.BookingService;
//import com.example.booking.Services.ServiceImpl.Grpc.ServiceClient;
import com.example.booking.Services.ServiceImpl.Grpc.ServiceClient;
import com.example.booking.Services.ServiceImpl.Grpc.TimeSlotClient;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ServiceClient serviceClient;
    @Autowired
    private TimeSlotClient timeSlotClient;
    @Override
    public ResponseEntity<?> bookingCreate(Integer userId, BookingDto bookingDto) {
//        userId = null;
        if(userId == null){
            log.info("user ID cannot be null.");
            throw new IllegalArgumentException("user ID cannot be null or empty.");
        }
        if(alreadyBooking(bookingDto.getVehicleId())){
            throw new BookingAlreadyExistsException(
                    "The booking already exists for vehicle ID: " + bookingDto.getVehicleId() + " within the last three days."
            );
        }

        BookingEntity bookingEntity = new BookingEntity();
        try{
            bookingEntity.setUserId(userId);
            bookingEntity.setVehicleId(bookingDto.getVehicleId());
            bookingEntity.setStatus(bookingDto.getStatus());

            if(bookingEntity.getStatus() == null){
                bookingEntity.setStatus(Status.PENDING);
            }

            if(bookingDto.getServiceId() != null){
                log.info("service id : "+ bookingDto.getServiceId());
                if(serviceClient.checkAvailability(bookingDto.getServiceId())){
                    bookingEntity.setServiceId(bookingDto.getServiceId());
                }
                else{
                    log.info("service ID not found.");
                    throw new com.example.booking.Exception.NoContentException("service ID not found.");
                }
            }

            if(bookingDto.getTimeSlotId() != null){
                log.info("slot id : "+ bookingDto.getTimeSlotId());
                if(timeSlotClient.checkSlotAvailablity(bookingDto.getTimeSlotId())){
                    bookingEntity.setTimeSlotId(bookingDto.getTimeSlotId());
                }
                else{
                    log.info("slot ID not found.");
                    throw new com.example.booking.Exception.NoContentException("slot ID not found.");
                }
            }

            bookingRepository.save(bookingEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body("successfully created");

        }catch (NoContentException e){
            throw e;
        }catch (IllegalArgumentException e){
            throw e;
        }catch(Exception e){
            log.error("Database constraint violation: " + e.getMessage());
            throw new DatabaseException("Internal server error", e);
        }

    }

    private boolean alreadyBooking(String vehicleId) {
        try{
            LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
            return bookingRepository.existingBooking(vehicleId, threeDaysAgo);
        }catch(Exception e){
            throw new DatabaseException("Internal server error", e);
        }
    }


    @Override
    public List<BookingEntity> allBookings() {
        try{
            List<BookingEntity> bookingDetails = bookingRepository.findAll();

            if(bookingDetails.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "There is no data");
            }
            return bookingDetails;
        }catch (ResponseStatusException e){
               throw e;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<List<BookingEntity>> getBookingDetails(Integer userId) {
        try{
            Optional<List<BookingEntity>> bookingDetails = bookingRepository.findByUserId(userId);
            if(bookingDetails.isPresent() && bookingDetails.get().isEmpty()){
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No bookings found for user ID: " + userId);
            }
            return bookingDetails;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BookingEntity updateBookings(Integer bookingId, BookingDetailsEditDto bookingDetailsEditDto) {
        if(bookingId == null){
            throw new IllegalArgumentException("booking id cannot be null");
        }
        try{
            Optional<BookingEntity> existingBooking = bookingRepository.findById(bookingId);

            if(existingBooking.isPresent()){
                BookingEntity updateBooking = existingBooking.get();
                if(bookingDetailsEditDto.getVehicleId() != null){
                    updateBooking.setVehicleId(bookingDetailsEditDto.getVehicleId());
                }
                if(bookingDetailsEditDto.getTimeSlotId() != null){
                    updateBooking.setTimeSlotId(bookingDetailsEditDto.getTimeSlotId());
                }
                if(bookingDetailsEditDto.getStatus() != null){
                    updateBooking.setStatus(bookingDetailsEditDto.getStatus());
                }
                updateBooking.setUpdatedAt(LocalDateTime.now());

                return bookingRepository.save(updateBooking);
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No bookings found for booking id: "+bookingId);
            }
        }catch (ResponseStatusException e){
            throw e;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing your request");
        }
    }
}
