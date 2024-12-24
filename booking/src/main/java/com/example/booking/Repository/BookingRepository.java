package com.example.booking.Repository;

import com.example.booking.Entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
    Optional<List<BookingEntity>> findByUserId(Integer userId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM BookingEntity b WHERE b.vehicleId = :vehicleId AND b.createdAt >= :threeDaysAgo")
    boolean existingBooking(@Param("vehicleId") String vehicleId, @Param("threeDaysAgo") LocalDateTime threeDaysAgo);
}
