package com.example.auth.Services.Service;


import com.example.auth.Dto.SignupDto;
import com.example.auth.Entity.UserEntity;
import org.springframework.http.ResponseEntity;


public interface SignupService {
    UserEntity registerCustomer(SignupDto signupDto);

    ResponseEntity<?> confirmEmail(String confirmationToken);
}
