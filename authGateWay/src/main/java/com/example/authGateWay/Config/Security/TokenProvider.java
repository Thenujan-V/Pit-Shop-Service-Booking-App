package com.example.authGateWay.Config.Security;



import com.example.authGateWay.Config.Security.ApplicationConfigProperties;
import com.example.authGateWay.Entity.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
@AllArgsConstructor
@Slf4j
public class TokenProvider implements Serializable {

    private final ApplicationConfigProperties applicationProperties;

    // Extract username from the token
    public String getUsernameFromToken(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from the token
    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parse token to extract claims
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //    Get signin key
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(applicationProperties.getJwt().getSigningKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate token for a user with a role
    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getUserEmail())
                .claim("role", user.getRole())
                .claim("user_id", user.getUser_id())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (applicationProperties.getJwt().getAccessTokenExpiration() * 1000 * 12 * 24)))

                .signWith(getSigningKey())
                .compact();
    }

    // Generate refresh token
//    public String generateRefreshToken(UserEntity user) {
//        return cryptoService.encryptRefreshToken(user.getUsername());
//    }

    // Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        log.info("ok");
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Retrieve authentication from token
    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final UserDetails userDetails) {
//        log.info("getAuthenticationToken");

        String role = getRoleFromToken(token); // Extract role from token
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                List.of(new SimpleGrantedAuthority(role))); // Use role directly
    }

    // Extract role from token
    public String getRoleFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class); // Directly retrieve role
    }

    // Extract user ID from token
    public Long getUserIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return Long.valueOf(claims.get("user_id").toString());
    }
}
