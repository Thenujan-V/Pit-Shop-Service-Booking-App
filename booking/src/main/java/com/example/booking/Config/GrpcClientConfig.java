package com.example.booking.Config;


import com.example.booking.proto.BookingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class GrpcClientConfig {
    @Value("${grpc.server.host}")
    private String grpcHost;
    @Value("${grpc.grpc-server.port}")
    private int grpcPort;
    private ManagedChannel managedChannel;
    @Bean
    public ManagedChannel managedChannel() {
        this.managedChannel = ManagedChannelBuilder
                .forAddress(grpcHost, grpcPort)
                .usePlaintext()  // Use plaintext (without TLS); you should enable TLS for production
                .build();
        return managedChannel;
    }
    @Bean
    public BookingServiceGrpc.BookingServiceBlockingStub bookingServiceBlockingStub(ManagedChannel channel) {
        return BookingServiceGrpc.newBlockingStub(channel);
    }
    @PreDestroy
    public void shutdownChannel() {
        if (managedChannel != null && !managedChannel.isShutdown()) {
            managedChannel.shutdown();
        }
    }
}
