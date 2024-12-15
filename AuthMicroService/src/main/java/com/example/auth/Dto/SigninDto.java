package com.example.auth.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninDto {
    @NotNull(message = "user name is required")
    private String userName;
    @NotNull(message = "Email is required")
    private String password;
}
