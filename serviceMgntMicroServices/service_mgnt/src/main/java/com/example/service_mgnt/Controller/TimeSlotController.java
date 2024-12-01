package com.example.service_mgnt.Controller;

import com.example.service_mgnt.Dto.SlotIdsRequestDto;
import com.example.service_mgnt.Dto.TimeSlotDto;
import com.example.service_mgnt.Dto.TimeSlotUpdateDto;
import com.example.service_mgnt.Entity.TimeSlotEntity;
import com.example.service_mgnt.Services.Service.TimeSlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/service-manager")
public class TimeSlotController {
    @Autowired
    private TimeSlotService timeSlotService;
    @PostMapping("/create-time-slots")
    private ResponseEntity<?> createTimeSlots(@Valid @RequestBody TimeSlotDto timeSlotDto){
        TimeSlotEntity savedTimeSlots = timeSlotService.timeSlotsCreated(timeSlotDto);
        return new ResponseEntity<>(savedTimeSlots, HttpStatus.CREATED);
    }

    @GetMapping("/get-time-slots")
    private ResponseEntity<?> getTimeSlots(){
        ResponseEntity<?> extractTimeSlots = timeSlotService.getSlots();
        return extractTimeSlots;
    }
    @PutMapping("/edit-time-slots/{slotId}")
    private ResponseEntity<TimeSlotEntity> editTimeSlots(@PathVariable Integer slotId, @RequestBody TimeSlotUpdateDto timeSlotUpdateDto){
        ResponseEntity<TimeSlotEntity> updateTimeSlots = timeSlotService.timeSlotsupdated(slotId, timeSlotUpdateDto);
        return updateTimeSlots;
    }

    @DeleteMapping("/delete-time-slots")
    private ResponseEntity<?> deleteTimeSlots(@RequestBody SlotIdsRequestDto slotIdsRequestDto){
        ResponseEntity<?> deleteTimeSlots = timeSlotService.timeSlotsDelete(slotIdsRequestDto.getSlotIds());
        return deleteTimeSlots;
    }
}
