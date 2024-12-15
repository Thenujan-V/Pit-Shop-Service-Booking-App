//package com.example.authGateWay.Services.ServiceImpl;
//
//import com.example.authGateWay.Config.Security.TokenProvider;
//import com.example.authGateWay.Dto.Response.SigninResponse;
//import com.example.authGateWay.Dto.SigninDto;
//import com.example.authGateWay.Entity.UserEntity;
//import com.example.authGateWay.Exception.CustomException;
//import com.example.authGateWay.Repository.UserRepository;
//import com.example.authGateWay.Services.Service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthServiceImpl implements AuthService {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private TokenProvider tokenProvider;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    @Override
//    public SigninResponse authSignin(SigninDto signinDto) {
//        System.out.println("un :"+ signinDto.getUserName());
//        try{
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signinDto.getUserName(), signinDto.getPassword());
//
//            Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//            UserEntity userEntity = userRepository.findByUserName(signinDto.getUserName());
//            if (userEntity == null) {
//                throw new UsernameNotFoundException("User with email " + signinDto.getUserName() + " not found");
//            }
//
//            final String token = tokenProvider.generateToken(userEntity);
//            SigninResponse signinResponse = new SigninResponse();
//            signinResponse.setAccessToken(token);
//
//            return signinResponse;
//        }
//        catch (UsernameNotFoundException ex) {
//            throw new CustomException("Invalid email or user not found", HttpStatus.NOT_FOUND);
//        } catch (BadCredentialsException ex) {
//            throw new CustomException("Invalid email or or password", HttpStatus.UNAUTHORIZED);
//        } catch (Exception ex) {
//            throw new CustomException("An error occurred during login", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//}
