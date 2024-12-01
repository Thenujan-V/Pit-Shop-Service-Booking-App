package com.example.service_mgnt.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Time;
import java.util.Date;

@Entity
@Data
@Table(name = "timeslot")
public class TimeSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id", nullable = false)
    private Integer slotId;
    @Column(name = "manager_id", nullable = false)
    private Integer managerId;
    @Column(name = "service_date", nullable = false)
    private Date serviceDate;
    @Column(name = "service_start_time", nullable = false)
    private Time serviceStartTime;
    @Column(name = "service_end_time", nullable = false)
    private Time serviceEndTime;
    @Column(name = "maximum_number_of_vehicles", nullable = false)
    private Integer maximumNumberOfVehicles;
    @Column(name = "vehicles_count", nullable = false)
    private Integer vehiclesCount = 0;
    @Column(name = "is_slot_active", nullable = false)
    private Boolean isSlotActive = false;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "updated_at", nullable = false)
    @CreationTimestamp
    private Date updatedAt;
}
