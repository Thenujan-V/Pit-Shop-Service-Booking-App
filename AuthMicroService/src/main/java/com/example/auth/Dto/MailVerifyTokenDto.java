package com.example.auth.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MailVerifyTokenDto {
//    @NotNull(message = "verification Code is required")
    private String verificationCode;
}
