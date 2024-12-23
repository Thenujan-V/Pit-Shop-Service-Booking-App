package com.example.service_mgnt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class  ServiceMgntApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceMgntApplication.class, args);
	}

}
