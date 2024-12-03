package com.example.booking.Services.Service;

import com.example.booking.Dto.BookingDto;
import com.example.booking.Entity.BookingEntity;

import java.util.List;

public interface BookingService {
    BookingEntity bookingCreate(Integer userId, BookingDto bookingDto);

    List<BookingEntity> allBookings();
}
