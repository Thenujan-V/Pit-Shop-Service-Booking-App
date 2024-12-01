package com.example.service_mgnt.Repository;

import com.example.service_mgnt.Entity.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Integer> {
}
