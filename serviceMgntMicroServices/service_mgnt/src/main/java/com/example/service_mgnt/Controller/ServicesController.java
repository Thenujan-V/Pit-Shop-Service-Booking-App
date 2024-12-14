package com.example.service_mgnt.Controller;

import com.example.service_mgnt.Dto.ServiceDetailsEditDto;
import com.example.service_mgnt.Dto.ServicesDto;
import com.example.service_mgnt.Entity.ServicesEntity;
import com.example.service_mgnt.Services.Service.ServicesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/api/v1/services")
public class ServicesController {
    @Autowired
    private ServicesService servicesService;
    @PostMapping("/create-services")
    private ResponseEntity<String> createServices(@Valid @RequestBody ServicesDto servicesDto){
        servicesService.servicesCreate(servicesDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("service successfully created");
    }

    @GetMapping("/get-services")
    private ResponseEntity<?> getServices(){
        List<ServicesEntity> listOfServices = servicesService.getServices();
        return ResponseEntity.ok(listOfServices);
    }

    @PatchMapping("/delete-status/{service_id}")
    private ResponseEntity<?> deleteServices(@PathVariable("service_id") Integer serviceId){
        return servicesService.removeService(serviceId);
    }

    @PatchMapping("/edit/{service_id}")
    private ResponseEntity<?> editServices(@PathVariable("service_id") Integer serviceId, @RequestBody Map<String, Object> updates){
        return servicesService.editService(serviceId, updates);
    }
}
