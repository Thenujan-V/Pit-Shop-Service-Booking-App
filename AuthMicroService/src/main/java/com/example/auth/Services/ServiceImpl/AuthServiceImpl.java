package com.example.auth.Services.ServiceImpl;


import com.example.auth.Config.Security.PasswordEncoder;
import com.example.auth.Config.Security.TokenProvider;
import com.example.auth.Dto.Response.SigninResponse;
import com.example.auth.Dto.SigninDto;
import com.example.auth.Entity.UserEntity;
import com.example.auth.Exception.CustomException;
import com.example.auth.Repository.UserRepository;
import com.example.auth.Services.Service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor

public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;


    @Override
    public SigninResponse authSignin(SigninDto signinDto) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signinDto.getUserName(), signinDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            UserEntity userEntity = userRepository.findByUserName(signinDto.getUserName());
            if (userEntity == null) {
                throw new UsernameNotFoundException("User with email " + signinDto.getUserName() + " not found");
            }

            final String token = tokenProvider.generateToken(userEntity);
            SigninResponse signinResponse = new SigninResponse();
            signinResponse.setAccessToken(token);

            return signinResponse;
        }
        catch (UsernameNotFoundException ex) {
            throw new CustomException("Invalid email or user not found", HttpStatus.NOT_FOUND);
        } catch (BadCredentialsException ex) {
            throw new CustomException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            throw new CustomException("An error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updatePassowrd(Integer userId, String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        UserEntity userEntity = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + userId + " not found"));
        userEntity.setPassword(passwordEncoder.encodePassword(password));
        userRepository.save(userEntity);
    }
}
