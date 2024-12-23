package com.example.service_mgnt.Services.ServiceImpl;

import com.example.service_mgnt.Dto.ServicesDto;
import com.example.service_mgnt.Entity.ServicesEntity;
import com.example.service_mgnt.Exception.DatabaseException;
import com.example.service_mgnt.Exception.NoContentException;
import com.example.service_mgnt.Repository.ServicesRepository;
import com.example.service_mgnt.Services.Service.ServicesService;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    @Autowired
    private ServicesRepository servicesRepository;
    @Override
    public void servicesCreate(ServicesDto servicesDto) {
        try{
            ServicesEntity servicesEntity = new ServicesEntity();
            servicesEntity.setServiceName(servicesDto.getServiceName());
            servicesEntity.setServicePrice(servicesDto.getServicePrice());

            servicesRepository.save(servicesEntity);

        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("Failed to save the services due to a database constraint.",e);
        }
    }

    @Override
    public List<ServicesEntity> getServices() {
        try{
            List<ServicesEntity> listOfServices = servicesRepository.findAll();
            if(listOfServices.isEmpty()){
                throw new NoSuchElementException("No services found in the database.");
            }
            return listOfServices;
        }catch (NoSuchElementException e) {
            throw e;
        }catch (Exception e){
            throw new DatabaseException("Failed to fetch time slots due to a database error.", e);
        }
    }

    @Override
    public ResponseEntity<?> removeService(Integer serviceId) {
        if(serviceId == null){
            throw new IllegalArgumentException("service id cannot be null.");
        }
        try{
            Optional<ServicesEntity> serviceDetails = servicesRepository.findById(serviceId);
            if(serviceDetails.isPresent()){
                ServicesEntity service = serviceDetails.get();

                service.setIsAvailable(service.getIsAvailable() ? false : true);
                servicesRepository.save(service);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("success");
            }
            else {
                throw new NoContentException("There is no content.");
            }

        }catch (NoContentException e){
            throw e;
        }catch (IllegalArgumentException e){
            throw e;
        }catch (Exception e){
            throw new DatabaseException("Internal server error");
        }
    }

    @Override
    public ResponseEntity<?> editService(Integer serviceId, Map<String, Object> updates) {
            if(serviceId == null){
                throw new IllegalArgumentException("service id cannot be null.");
            }
            Optional<ServicesEntity> serviceOptional = servicesRepository.findById(serviceId);
            if(!serviceOptional.isPresent()){
                throw new NoContentException("service not found");
            }
            try{
                ServicesEntity serviceDetails = serviceOptional.get();
                updates.forEach((key, value) -> {
                    switch (key){
                        case "serviceName":
                            serviceDetails.setServiceName((String) value);
                            System.out.println("name : "+value);
                            break;
                        case "servicePrice":
                            System.out.println("price : "+value);
                            serviceDetails.setServicePrice((Integer) value);
                            break;
                        default:
                            throw new IllegalArgumentException("field " + key + " not updatable.");
                    }
                });
                servicesRepository.save(serviceDetails);

                return ResponseEntity.accepted().body("success");

            }catch (IllegalArgumentException e){
                throw e;
            }catch (NoContentException   e){
                throw e;
            }catch (Exception e){
                throw new DatabaseException("Internal Server Error.");
            }
    }


}
