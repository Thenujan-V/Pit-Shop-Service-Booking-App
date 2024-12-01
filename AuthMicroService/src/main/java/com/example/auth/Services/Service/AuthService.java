package com.example.auth.Services.Service;


import com.example.auth.Dto.Response.SigninResponse;
import com.example.auth.Dto.SigninDto;

public interface AuthService {
    public SigninResponse authSignin(SigninDto signinDto);

    void updatePassowrd(Integer userId, String password);
}
