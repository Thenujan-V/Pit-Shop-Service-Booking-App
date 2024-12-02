package com.example.booking.Services.ServiceImpl;

import com.example.booking.Dto.BookingDto;
import com.example.booking.Entity.BookingEntity;
import com.example.booking.Enum.Status;
import com.example.booking.Exception.ConflictException;
import com.example.booking.Repository.BookingRepository;
import com.example.booking.Services.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
