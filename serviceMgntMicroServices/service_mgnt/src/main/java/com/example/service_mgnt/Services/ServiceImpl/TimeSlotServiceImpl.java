package com.example.service_mgnt.Services.ServiceImpl;

import com.example.service_mgnt.Dto.TimeSlotDto;
import com.example.service_mgnt.Dto.TimeSlotUpdateDto;
import com.example.service_mgnt.Entity.TimeSlotEntity;
import com.example.service_mgnt.Exception.DatabaseException;
import com.example.service_mgnt.Exception.NoContentException;
import com.example.service_mgnt.Repository.TimeSlotRepository;
import com.example.service_mgnt.Services.Service.TimeSlotService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Override
    public TimeSlotEntity timeSlotsCreated(TimeSlotDto timeSlotDto) {
        if(timeSlotDto == null){
            throw new IllegalArgumentException("Time slots creation data is invalid");
        }
        TimeSlotEntity timeSlotEntity = new TimeSlotEntity();
        try{
            timeSlotEntity.setManagerId(timeSlotDto.getManagerId());
            timeSlotEntity.setServiceDate(timeSlotDto.getServiceDate());
            timeSlotEntity.setServiceStartTime(timeSlotDto.getServiceStartTime());
            timeSlotEntity.setServiceEndTime(timeSlotDto.getServiceEndTime());
            timeSlotEntity.setMaximumNumberOfVehicles(timeSlotDto.getMaximumNumberOfVehicles());

            TimeSlotEntity savedTimeSlots = timeSlotRepository.save(timeSlotEntity);

            return savedTimeSlots;

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Failed to save the time slot due to a database constraint.");
        }
    }

    @Override
    public ResponseEntity<TimeSlotEntity> timeSlotsupdated(Integer slotId, TimeSlotUpdateDto timeSlotUpdateDto) {
        Optional<TimeSlotEntity> existingTimeSlotOpt = timeSlotRepository.findById(slotId);

        if(existingTimeSlotOpt.isPresent()){
            TimeSlotEntity timeSlotEntity = existingTimeSlotOpt.get();

            if(timeSlotUpdateDto.getServiceDate() != null){
                timeSlotEntity.setServiceDate(timeSlotUpdateDto.getServiceDate());
            }
            if(timeSlotUpdateDto.getServiceStartTime() != null){
                timeSlotEntity.setServiceStartTime(timeSlotUpdateDto.getServiceStartTime());
            }
            if(timeSlotUpdateDto.getServiceEndTime() != null){
                timeSlotEntity.setServiceEndTime(timeSlotUpdateDto.getServiceEndTime());
            }
            if(timeSlotUpdateDto.getMaximumNumberOfVehicles() != null){
                timeSlotEntity.setMaximumNumberOfVehicles(timeSlotUpdateDto.getMaximumNumberOfVehicles());
            }

            try{
                timeSlotRepository.save(timeSlotEntity);
            }catch(DataIntegrityViolationException e){
                throw new DatabaseException("Failed to save the time slot due to a database constraint.");
            }

            return ResponseEntity.ok(timeSlotEntity);
        }

        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> timeSlotsDelete(List<Integer> slotIds) {
        List<Integer> notFoundIds = new ArrayList<>();
        try {
            for(Integer slotId : slotIds){
                Optional<TimeSlotEntity> existingTimeSlotOpt = timeSlotRepository.findById(slotId);
                if (existingTimeSlotOpt.isPresent()) {
                    timeSlotRepository.deleteById(slotId);
                }
                else{
                    notFoundIds.add(slotId);
                }
            }

            if (notFoundIds.isEmpty()) {
                return ResponseEntity.ok("Selected time slots deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The following time slots were not found: " + notFoundIds);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting the time slot.");
        }
    }

    @Override
    public List<TimeSlotEntity> getSlots() {
        try{
            List<TimeSlotEntity> timeSlots = timeSlotRepository.findAll();
            if(timeSlots.isEmpty()){
                throw new NoContentException("No time slots available.");
            }
            return timeSlots;
        }catch(DataAccessException e){
            throw new DatabaseException("Failed to fetch time slots due to a database error.", e);
        }
    }

    @Override
    public ResponseEntity<TimeSlotEntity> vehicleCountUpdated(Integer slotId) {
        try{
            Optional<TimeSlotEntity> existingUser = timeSlotRepository.findById(slotId);
            if(existingUser.isPresent()){
                TimeSlotEntity timeSlotEntity = existingUser.get();
                if(timeSlotEntity.getMaximumNumberOfVehicles() != timeSlotEntity.getVehiclesCount()){
                    timeSlotEntity.setVehiclesCount(timeSlotEntity.getVehiclesCount() + 1);
                    timeSlotRepository.save(timeSlotEntity);
                    return ResponseEntity.ok(timeSlotEntity);
                }
                else{
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("Failed to save the time slot due to a database constraint.");
        }
    }
}
