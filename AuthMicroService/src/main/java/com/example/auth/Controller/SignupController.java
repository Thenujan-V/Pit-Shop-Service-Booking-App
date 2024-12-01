package com.example.auth.Controller;

import com.example.auth.Dto.MailVerifyTokenDto;
import com.example.auth.Dto.SignupDto;
import com.example.auth.Entity.UserEntity;
import com.example.auth.Services.Service.SignupService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @PostMapping("/signup")
    private ResponseEntity<UserEntity> customerRegister(@Valid @RequestBody SignupDto signupDto){
        UserEntity savedUser =  signupService.registerCustomer(signupDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/verify-email")
    private ResponseEntity<?> emailConfirmation(@Valid @RequestBody MailVerifyTokenDto mailVerifyTokenDto){
        String confirmationToken = mailVerifyTokenDto.getVerificationCode();
        ResponseEntity<?> confirmationResponse = signupService.confirmEmail(confirmationToken);
        return new ResponseEntity<>(confirmationResponse, HttpStatus.OK);
    }
}
