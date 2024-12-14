package com.example.service_mgnt.Services.Service;

import com.example.service_mgnt.Dto.ServicesDto;
import com.example.service_mgnt.Entity.ServicesEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ServicesService {
    void servicesCreate(ServicesDto servicesDto);

    List<ServicesEntity> getServices();

    ResponseEntity<?> removeService(Integer serviceId);

    ResponseEntity<?> editService(Integer serviceId, Map<String, Object> updates);
}
