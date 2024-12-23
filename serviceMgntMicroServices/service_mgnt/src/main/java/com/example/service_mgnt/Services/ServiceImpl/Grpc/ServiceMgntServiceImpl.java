package com.example.service_mgnt.Services.ServiceImpl.Grpc;

import com.example.service_mgnt.BookingServiceGrpc;
import com.example.service_mgnt.CheckAvailabilityRequest;
import com.example.service_mgnt.CheckAvailabilityResponse;
import com.example.service_mgnt.Repository.ServicesRepository;
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
    @Override
    public void checkAvailability(CheckAvailabilityRequest request, StreamObserver<CheckAvailabilityResponse> responseObserver){
        boolean existingService = servicesRepository.existsById(request.getServiceId());
        log.info("existing : "+ existingService);
        CheckAvailabilityResponse response = CheckAvailabilityResponse.newBuilder()
                .setIsAvailable(existingService)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
