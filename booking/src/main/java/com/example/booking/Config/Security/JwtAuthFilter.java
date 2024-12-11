package com.example.booking.Config.Security;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Service
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private ApplicationConfigProperties applicationProperties;


    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException, ServletException {
        logger.info("doFilterInternal");
        String header = req.getHeader(applicationProperties.getJwt().getHeaderString());
        String username = null;
        String authToken = null;

        if (header != null && header.startsWith(applicationProperties.getJwt().getTokenPrefix())) {
            authToken = header.replace(applicationProperties.getJwt().getTokenPrefix() + " ", "");

            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("Error occurred while retrieving Username from Token", e);
                sendErrorResponse(res, HttpStatus.UNAUTHORIZED, "auth.username.cannot.retrieve");
                return;
            } catch (ExpiredJwtException e) {
                logger.warn("The token has expired");
                sendErrorResponse(res, HttpStatus.UNAUTHORIZED, "auth.token.expired");
                return;
            } catch (SignatureException e) {
                logger.error("Authentication Failed. Invalid username or password.");
                sendErrorResponse(res, HttpStatus.UNAUTHORIZED, "auth.signature.invalid");
                return;
            } catch (Exception e) {
                logger.error("Unknown exception ", e);
                sendErrorResponse(res, HttpStatus.UNAUTHORIZED, e.getMessage());
                return;
            }
        } else {
            logger.warn("Bearer string not found, ignoring the header");
        }



        chain.doFilter(req, res);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}