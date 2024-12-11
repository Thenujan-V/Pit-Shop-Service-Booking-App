package com.example.booking.Services.ServiceImpl;

import com.example.booking.Dto.BookingDetailsEditDto;
import com.example.booking.Dto.BookingDto;
import com.example.booking.Entity.BookingEntity;
import com.example.booking.Enum.Status;
import com.example.booking.Exception.ConflictException;
import com.example.booking.Repository.BookingRepository;
import com.example.booking.Services.Service.BookingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Override
    public BookingEntity bookingCreate(Integer userId, BookingDto bookingDto) {
        if(userId == null){
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"User id cannot be null or empty");
        }

        BookingEntity bookingEntity = new BookingEntity();
        try{
            bookingEntity.setUserId(userId);
            bookingEntity.setVehicleId(bookingDto.getVehicleId());
            bookingEntity.setTimeSlotId(bookingDto.getTimeSlotId());
            bookingEntity.setStatus(bookingDto.getStatus());

            if(bookingEntity.getStatus() == null){
                bookingEntity.setStatus(Status.PENDING);
            }

            return bookingRepository.save(bookingEntity);

        }catch (ResponseStatusException e){
            throw e;
        }catch(Exception e){
            throw new ConflictException("Database constraint violation", e);
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
