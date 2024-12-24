package com.example.booking.Services.ServiceImpl.Grpc;

import com.example.booking.proto.BookingServiceGrpc;

import com.example.booking.proto.CheckSlotAvailabilityRequest;
import com.example.booking.proto.CheckSlotAvailabilityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TimeSlotClient {
    @Autowired
    private BookingServiceGrpc.BookingServiceBlockingStub bookingServiceBlockingStub;
    public Boolean checkSlotAvailablity(Integer slot_id){
        log.info("service_id is come to checkAvailability "+slot_id);
        CheckSlotAvailabilityRequest request = CheckSlotAvailabilityRequest.newBuilder()
                .setSlotId(slot_id)
                .build();

        CheckSlotAvailabilityResponse response = bookingServiceBlockingStub.checkSlotAvailability(request);

        return response.getIsAvailable();
    }
}
