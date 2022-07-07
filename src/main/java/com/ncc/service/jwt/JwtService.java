package com.ncc.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService implements Serializable {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    private final long JWT_EXP = 7 * 3600 * 24 * 1000;
    private final long JWT_EXP_REFRESH = 1000 * 3600 * 24 * 365;

    public String extractUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    public String generateAccessToken(UserDetails userDetails) {

        return generateToken(userDetails)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXP))
                .compact();
    }

    public String generateAccessToken(String username) {

        return generateToken(username)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXP))
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {

        return generateToken(userDetails)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXP_REFRESH))
                .compact();
    }

    public String generateRefreshToken(String username) {

        return generateToken(username)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXP_REFRESH))
                .compact();
    }

    private JwtBuilder generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // --> extract username in subject
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private JwtBuilder generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // --> extract username in subject
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        final String username = extractUsernameFromToken(token);

        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
