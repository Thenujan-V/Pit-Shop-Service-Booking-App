package com.example.authGateWay.Config.Security;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private ApplicationConfigProperties applicationProperties;

//    @Autowired
//    @Resource(name = "jwtUserDetailsService")
//    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException, ServletException {
        String header = req.getHeader(applicationProperties.getJwt().getHeaderString());
        String username = null;
        String authToken = null;

        if (header != null && header.startsWith(applicationProperties.getJwt().getTokenPrefix())) {
            authToken = header.replace(applicationProperties.getJwt().getTokenPrefix() + " ", "");

            try {
                if(jwtTokenUtil.validateToken(authToken)){
                    username = jwtTokenUtil.getUsernameFromToken(authToken);
                    String role = jwtTokenUtil.getRoleFromToken(authToken);

                    System.out.println("role and username :"+username+ " " +role);
                    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    System.out.println("authentication : "+authentication);
                }
            } catch (IllegalArgumentException e) {
                sendErrorResponse(res, HttpStatus.UNAUTHORIZED, "auth.username.cannot.retrieve");
                return;
            } catch (ExpiredJwtException e) {
                sendErrorResponse(res, HttpStatus.UNAUTHORIZED, "auth.token.expired");
                return;
            } catch (SignatureException e) {
                sendErrorResponse(res, HttpStatus.UNAUTHORIZED, "auth.signature.invalid");
                return;
            } catch (Exception e) {
                sendErrorResponse(res, HttpStatus.UNAUTHORIZED, e.getMessage());
                return;
            }
        } else {
            sendErrorResponse(res, HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header.");
            return;
        }

//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
//
//                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(authToken, userDetails);
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }

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
