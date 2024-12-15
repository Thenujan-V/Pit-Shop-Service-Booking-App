package com.example.authGateWay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthGateWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthGateWayApplication.class, args);
	}

}
