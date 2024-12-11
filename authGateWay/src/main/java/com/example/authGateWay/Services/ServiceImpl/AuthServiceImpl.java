package com.example.authGateWay.Services.ServiceImpl;

import com.example.authGateWay.Config.Security.TokenProvider;
import com.example.authGateWay.Dto.Response.SigninResponse;
import com.example.authGateWay.Dto.SigninDto;
import com.example.authGateWay.Entity.UserEntity;
import com.example.authGateWay.Exception.CustomException;
import com.example.authGateWay.Repository.UserRepository;
import com.example.authGateWay.Services.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public SigninResponse authSignin(SigninDto signinDto) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signinDto.getUser_email(), signinDto.getPassword());
            System.out.println("test 11 : "+signinDto.getPassword());
            System.out.println("test 12");
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            System.out.println("test 13 {authentication} : "+ authentication);

            UserEntity userEntity = userRepository.findByUserEmail(signinDto.getUser_email());
            if (userEntity == null) {
                throw new UsernameNotFoundException("User with email " + signinDto.getUser_email() + " not found");
            }

            final String token = tokenProvider.generateToken(userEntity);
            SigninResponse signinResponse = new SigninResponse();
            signinResponse.setAccessToken(token);

            return signinResponse;
        }
        catch (UsernameNotFoundException ex) {
            throw new CustomException("Invalid email or user not found", HttpStatus.NOT_FOUND);
        } catch (BadCredentialsException ex) {
            throw new CustomException("Invalid email or or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            throw new CustomException("An error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
