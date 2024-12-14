package com.example.service_mgnt.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ServicesDto {
    @NotNull(message = "service name required")
    private String serviceName;
    @NotNull(message = "service price required")
    private Integer servicePrice;
}
