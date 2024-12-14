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
    private ResponseEntity<TimeSlotEntity> createTimeSlots(@Valid @RequestBody TimeSlotDto timeSlotDto){
        TimeSlotEntity savedTimeSlots = timeSlotService.timeSlotsCreated(timeSlotDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTimeSlots);
    }

    @GetMapping("/get-time-slots")
    private ResponseEntity<List<TimeSlotEntity>> getTimeSlots(){
//        System.out.println("okey");
        List<TimeSlotEntity> extractTimeSlots = timeSlotService.getSlots();
        return ResponseEntity.ok(extractTimeSlots);
    }
    @PutMapping("/edit-time-slots/{slotId}")
    private ResponseEntity<TimeSlotEntity> editTimeSlots(@PathVariable Integer slotId, @RequestBody TimeSlotUpdateDto timeSlotUpdateDto){
        ResponseEntity<TimeSlotEntity> updateTimeSlots = timeSlotService.timeSlotsupdated(slotId, timeSlotUpdateDto);
        return ResponseEntity.ok(updateTimeSlots.getBody());
    }

    @DeleteMapping("/delete-time-slots")
    private ResponseEntity<String> deleteTimeSlots(@RequestBody SlotIdsRequestDto slotIdsRequestDto){
        timeSlotService.timeSlotsDelete(slotIdsRequestDto.getSlotIds());
        return ResponseEntity.ok("Time slots deleted successfully");
    }

    @PutMapping("/edit-vehicle-count/{slotId}")
    private ResponseEntity<TimeSlotEntity> editVehicleCount(@PathVariable Integer slotId){
        ResponseEntity<TimeSlotEntity> updateVehicleCount = timeSlotService.vehicleCountUpdated(slotId);
        return ResponseEntity.ok(updateVehicleCount.getBody());
    }
}
