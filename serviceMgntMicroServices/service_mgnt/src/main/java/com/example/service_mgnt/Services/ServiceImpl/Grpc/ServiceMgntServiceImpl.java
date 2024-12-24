package com.example.service_mgnt.Services.ServiceImpl.Grpc;

import com.example.service_mgnt.*;
import com.example.service_mgnt.Repository.ServicesRepository;
import com.example.service_mgnt.Repository.TimeSlotRepository;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.grpc.stub.StreamObserver;

@GrpcService
@Slf4j
public class ServiceMgntServiceImpl extends BookingServiceGrpc.BookingServiceImplBase {
    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Override
    public void checkAvailability(CheckAvailabilityRequest request, StreamObserver<CheckAvailabilityResponse> responseObserver){
        boolean existingService = servicesRepository.existsByIdAndIsAvailable(request.getServiceId(), true);
        log.info("existing : "+ existingService);
        CheckAvailabilityResponse response = CheckAvailabilityResponse.newBuilder()
                .setIsAvailable(existingService)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void checkSlotAvailability(CheckSlotAvailabilityRequest request, StreamObserver<CheckSlotAvailabilityResponse> responseObserver){
        log.info("test 1");
        boolean existingSlot = timeSlotRepository.existsById(request.getSlotId());
        log.info("existing : "+ existingSlot);
        CheckSlotAvailabilityResponse response = CheckSlotAvailabilityResponse.newBuilder()
                .setIsAvailable(existingSlot)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
