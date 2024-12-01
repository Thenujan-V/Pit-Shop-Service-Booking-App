package com.example.auth.Services.ServiceImpl;

import com.example.auth.Config.Security.PasswordEncoder;
import com.example.auth.Dto.SignupDto;
import com.example.auth.Entity.UserEntity;
import com.example.auth.Exception.ConflictException;
import com.example.auth.Repository.UserRepository;
import com.example.auth.Services.Service.SignupService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class SignupServiceImpl implements SignupService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public UserEntity registerCustomer(SignupDto signupDto) {

        if(signupDto == null){
            throw new IllegalArgumentException("Customer signup data is invalid");
        }

        if (userRepository.existsByUserEmail(signupDto.getUser_email())) {
            throw new ConflictException("Email is already in use");
        }

        UserEntity userEntity = new UserEntity();

        try {
            userEntity.setUserName(signupDto.getUser_name());
            userEntity.setUserEmail(signupDto.getUser_email());
            userEntity.setRole(signupDto.getRole());
            userEntity.setAddress(signupDto.getAddress());
            userEntity.setGender(signupDto.getGender());
            userEntity.setPhone_no(signupDto.getPhone_no());

            try {
                userEntity.setPassword(passwordEncoder.encodePassword(signupDto.getPassword()));
            } catch (Exception e) {
                throw new ServiceException("Password encoding failed", e);
            }

            String token = UUID.randomUUID().toString();
            userEntity.setVerificationCode(token);

            UserEntity savedUser = userRepository.save(userEntity);

            try{
//                emailService.sendVerificationEmail(signupDto.getUser_email(), token);
            }catch (Exception e){
                throw new ServiceException("Failed to send verification email", e);
            }

            return savedUser;
        }catch (Exception e){
            throw new ConflictException("Database constraint violation", e);
        }
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        UserEntity userEntity = userRepository.findByVerificationCode(confirmationToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));

        try{
            userEntity.setEnabled(true);
            userEntity.setVerificationCode(null);
            userRepository.save(userEntity);

            return ResponseEntity.ok("Email verified successfully!");
        }catch(Exception e){
            throw new ConflictException("Database constraint violation", e);
        }
    }
}
