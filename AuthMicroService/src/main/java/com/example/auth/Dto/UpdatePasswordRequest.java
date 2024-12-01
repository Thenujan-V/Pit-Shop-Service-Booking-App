package com.example.auth.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
    @NotBlank(message = "updatePassword.password.null")
    String password;
}
