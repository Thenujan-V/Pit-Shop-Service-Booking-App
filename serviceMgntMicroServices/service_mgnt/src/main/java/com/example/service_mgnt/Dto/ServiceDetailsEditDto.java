package com.example.service_mgnt.Dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ServiceDetailsEditDto {
    private String serviceName;
    private Integer servicePrice;
}
