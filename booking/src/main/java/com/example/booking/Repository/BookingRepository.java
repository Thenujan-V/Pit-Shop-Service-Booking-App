package com.example.booking.Repository;

import com.example.booking.Entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
    Optional<List<BookingEntity>> findByUserId(Integer userId);
}
