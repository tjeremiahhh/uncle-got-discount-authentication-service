package com.security.authenticationservice.config;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.security.authenticationservice.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private static final String SECRET_KEY = "IRGPRsZoK7daSffp4SEeNAV8NRKG/TZ09LeH3Qr94+AL1XE7BZuUxoIfXWXfNmzOxbD8rH5zzqDiTFj39Wq+ezAignBNT8DAO2Ca8b5+e8/ieATJ33RwMuHp+KmvMPBs4I0caVMHlqL2rKZSEt4Th3r1tsUW/aky2CLeizRrMpBUaddvnvzKgzWKaSp1Z7PWHKIrExp3q4zDSA7cA/HHIL6n78UsLI+5DnV1qGQ/uV0Qcta+v7+fWhEggTxFOEaRIQjFlbrlkv+nql63WvLcVX4mRwWJoPzXh9NN5hdIfFipMZfDsBYnMyWKjPS2o//URB/v8M5B7D3k7dMEg0Y42/8jWmlzCU6QXR/A5ppYPis=";

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user) {
        return Jwts
            .builder()
            .setHeaderParam("typ", "JWT")
            .claim("id", user.getId())
            .claim("name", user.getName())
            .claim("emailAddress", user.getEmailAddress())
            .claim("phoneNumber", user.getPhoneNumber())
            .claim("isBusinessOwner", user.getIsBusinessOwner())
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
