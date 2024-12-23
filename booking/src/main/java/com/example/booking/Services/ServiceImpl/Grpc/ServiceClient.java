package com.example.booking.Services.ServiceImpl.Grpc;

import com.example.booking.proto.BookingServiceGrpc;
import com.example.booking.proto.CheckAvailabilityRequest;
import com.example.booking.proto.CheckAvailabilityResponse;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ServiceClient {
//    @GrpcClient("booking-service")
    @Autowired
    private BookingServiceGrpc.BookingServiceBlockingStub bookingServiceBlockingStub;

    public Boolean checkAvailability(Integer service_id){
        log.info("service_id is come to checkAvailability "+service_id);
        CheckAvailabilityRequest request = CheckAvailabilityRequest.newBuilder()
                .setServiceId(service_id)
                .build();

        CheckAvailabilityResponse response = bookingServiceBlockingStub.checkAvailability(request);

        return response.getIsAvailable();
    }
}
