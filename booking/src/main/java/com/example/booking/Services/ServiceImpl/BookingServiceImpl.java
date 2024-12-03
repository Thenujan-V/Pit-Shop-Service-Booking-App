package com.example.booking.Services.ServiceImpl;

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

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Override
    public BookingEntity bookingCreate(Integer userId, BookingDto bookingDto) {
        if(userId == null){
            throw new IllegalArgumentException("User id cannot be null or empty");
        }

        BookingEntity bookingEntity = new BookingEntity();
        try{
            bookingEntity.setUserId(userId);
            bookingEntity.setVehicleId(bookingDto.getVehicleId());
            bookingEntity.setTimeSlotId(bookingDto.getTimeSlotId());
            bookingEntity.setStatus(bookingDto.getStatus());

            if(bookingEntity.getStatus() == null){
                bookingEntity.setStatus(Status.BOOKING);
            }

            return bookingRepository.save(bookingEntity);

        }catch(Exception e){
            throw new ConflictException("Database constraint violation", e);
        }

    }

    @Override
    public List<BookingEntity> allBookings() {
        try{
            System.out.println("okey 1");
            List<BookingEntity> bookingDetails = bookingRepository.findAll();

            if(bookingDetails.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "There is no data");
            }
            return bookingDetails;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
