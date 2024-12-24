//package com.example.service_mgnt.Services.ServiceImpl.Grpc;
//
//import com.example.service_mgnt.*;
//import com.example.service_mgnt.Repository.TimeSlotRepository;
//import io.grpc.stub.StreamObserver;
//import lombok.extern.slf4j.Slf4j;
//import net.devh.boot.grpc.server.service.GrpcService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@GrpcService
//@Slf4j
//public class CheckTimeSlotImpl extends BookingServiceGrpc.BookingServiceImplBase {
//    @Autowired
//    private TimeSlotRepository timeSlotRepository;
//    @Override
//    public void checkSlotAvailability(CheckSlotAvailabilityRequest request, StreamObserver<CheckSlotAvailabilityResponse> responseObserver){
//        log.info("test 1");
//        boolean existingSlot = timeSlotRepository.existsById(request.getSlotId());
//        log.info("existing : "+ existingSlot);
//        CheckSlotAvailabilityResponse response = CheckSlotAvailabilityResponse.newBuilder()
//                .setIsAvailable(existingSlot)
//                .build();
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//}
