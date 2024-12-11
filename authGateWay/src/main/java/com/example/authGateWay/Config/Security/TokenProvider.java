package com.example.authGateWay.Config.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
@AllArgsConstructor
@Slf4j
public class TokenProvider implements Serializable {

    private final ApplicationConfigProperties applicationProperties;
    public String getUsernameFromToken(String token) {

        return extractClaim(token, Claims::getSubject);
    }
    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(applicationProperties.getJwt().getSigningKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        final String role = getRoleFromToken(token);
        return (!isTokenExpired(token));
    }

    public String getRoleFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class); // Directly retrieve role
    }
    public Long getUserIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return Long.valueOf(claims.get("user_id").toString());
    }
}
