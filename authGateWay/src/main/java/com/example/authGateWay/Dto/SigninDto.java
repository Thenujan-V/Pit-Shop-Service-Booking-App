package com.example.authGateWay.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninDto {
    @Email(message = "Invalid email")
    @NotNull(message = "Email is required")
    private String user_email;
    @NotNull(message = "Email is required")
    private String password;
}
