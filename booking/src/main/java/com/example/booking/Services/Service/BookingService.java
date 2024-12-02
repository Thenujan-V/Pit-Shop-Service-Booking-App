package com.example.booking.Services.Service;

import com.example.booking.Dto.BookingDto;
import com.example.booking.Entity.BookingEntity;

public interface BookingService {
    BookingEntity bookingCreate(Integer userId, BookingDto bookingDto);
}
