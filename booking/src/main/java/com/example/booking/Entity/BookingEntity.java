package com.example.booking.Entity;

import com.example.booking.Enum.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "booking")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false)
    private Integer bookingId;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "vehicle_id", nullable = false)
    private String vehicleId;
    @Column(name = "time_slot_id", nullable = false)
    private Integer timeSlotId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id", insertable = false, updatable = false)
    @JsonIgnore
    private VehicleEntity vehicleEntity;
}
