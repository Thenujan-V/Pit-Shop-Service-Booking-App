package com.example.auth.Controller;

import com.example.auth.Config.Security.ApplicationConfigProperties;
import com.example.auth.Config.Security.TokenProvider;
import com.example.auth.Dto.Response.SigninResponse;
import com.example.auth.Dto.SigninDto;
import com.example.auth.Dto.UpdatePasswordRequest;
import com.example.auth.Services.Service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

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

    @PutMapping("/password-update")
    public ResponseEntity<?> updateMyPassword(@RequestHeader("Authorization") String token, @Validated @RequestBody UpdatePasswordRequest passwordRequest){
        System.out.println("token : "+token);
        if (!token.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token format");
        }

        String authToken = token.replace(TOKEN_PREFIX + " ", "");

        Integer userId = Math.toIntExact(tokenProvider.getUserIdFromToken(authToken));
        try {
            authService.updatePassowrd(userId, passwordRequest.getPassword());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("Password updated successfully");
    }
}
