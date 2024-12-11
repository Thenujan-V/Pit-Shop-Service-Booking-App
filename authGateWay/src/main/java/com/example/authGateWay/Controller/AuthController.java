package com.example.authGateWay.Controller;

import com.example.authGateWay.Config.Security.ApplicationConfigProperties;
import com.example.authGateWay.Config.Security.TokenProvider;
import com.example.authGateWay.Dto.Response.SigninResponse;
import com.example.authGateWay.Dto.SigninDto;
import com.example.authGateWay.Services.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth-gate-way")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private ApplicationConfigProperties applicationConfigProperties;

    private static final String TOKEN_PREFIX = "Bearer";

    @PostMapping("/signin")
    private ResponseEntity<SigninResponse> authSignin(@Valid @RequestBody SigninDto signinDto){
        SigninResponse response = authService.authSignin(signinDto);
        return ResponseEntity.ok(response);
    }
}
