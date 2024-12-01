package com.example.service_mgnt.Services.Service;

import com.example.service_mgnt.Dto.TimeSlotDto;
import com.example.service_mgnt.Dto.TimeSlotUpdateDto;
import com.example.service_mgnt.Entity.TimeSlotEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TimeSlotService {
    TimeSlotEntity timeSlotsCreated(TimeSlotDto timeSlotDto);

    ResponseEntity<TimeSlotEntity> timeSlotsupdated(Integer slotId, TimeSlotUpdateDto timeSlotUpdateDto);

    ResponseEntity<?> timeSlotsDelete(List<Integer> slotIds);

    ResponseEntity<List<TimeSlotEntity>> getSlots();
}
