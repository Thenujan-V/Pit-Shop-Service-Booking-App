package com.example.booking.Config;


import com.example.booking.Config.Security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("okey 1");

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/v1/booking/create-booking").permitAll()
                                .requestMatchers("/api/v1/booking/get-all-booking-details").permitAll()
                                .requestMatchers("/api/v1/booking/get-user-booking/{user_id}").permitAll()
                                .requestMatchers("/api/v1/booking/edit-bookings/{booking_id}").permitAll()
                                .requestMatchers("/api/v1/vehicle/register-vehicle").permitAll()
                                .requestMatchers("/api/v1/vehicle/get-vehicle-details/{userId}").permitAll()
                                .requestMatchers("/api/v1/vehicle/edit-vehicle-details/{vehicle_id}").permitAll()
                                .requestMatchers("/api/v1/vehicle/get-all-vehicles").permitAll()
                                .requestMatchers("/api/v1/vehicle/delete-vehicle/{vehicle_id}").permitAll()

                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        System.out.println("okey 2");
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public JwtAuthFilter authenticationTokenFilterBean() throws Exception {
        System.out.println("test 2");
        return new JwtAuthFilter();
    }
}
