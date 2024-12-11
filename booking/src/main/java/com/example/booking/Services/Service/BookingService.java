package com.example.booking.Services.Service;

import com.example.booking.Dto.BookingDetailsEditDto;
import com.example.booking.Dto.BookingDto;
import com.example.booking.Entity.BookingEntity;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    BookingEntity bookingCreate(Integer userId, BookingDto bookingDto);

    List<BookingEntity> allBookings();

    Optional<List<BookingEntity>> getBookingDetails(Integer userId);

    BookingEntity updateBookings(Integer bookingId, BookingDetailsEditDto bookingDetailsEditDto);
}
