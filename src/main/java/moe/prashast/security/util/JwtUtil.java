package moe.prashast.security.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import moe.prashast.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
@Component
public class JwtUtil {

    @Value("${access.token.expiry.time}")
    private long accessExpiryTime;

    @Value("${refresh.token.expiry.time}")
    private long refreshExpiryTime;

    private final String SECRET = "Zy9X!aPq3L7nT$kR4wV6mC2uB8eD5jF1hG0sY#vQ";

//    private static final Duration ACCESS_DURATION = Duration.ofSeconds(accessExpiryTime);   // 1 Day
//    private static final Duration REFRESH_DURATION = Duration.ofSeconds(refreshExpiryTime); // 7 Days


    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public Duration getAccessDuration() {
        return Duration.ofSeconds(accessExpiryTime);
    }

    public Duration getRefreshDuration() {
        return Duration.ofSeconds(refreshExpiryTime);
    }

    public TokenResponse generateAccessToken(String username) {
//        long EXPIRATION = 1000L * 60 * 15; // 15 minutes
//        long EXPIRATION = 1000L * 60 * 60 * 24 * 10; // 10 days
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Duration.ofSeconds(accessExpiryTime).toMillis());

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("type", "ACCESS")
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        return new TokenResponse(token, expiryDate);
    }

    public TokenResponse generateRefreshToken(String username) {

//        long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 30; //  30 day

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Duration.ofSeconds(refreshExpiryTime).toMillis());

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("type", "REFRESH")
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        return new TokenResponse(token, expiryDate);
    }



    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getClaim(String token, String claimKey) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(claimKey, String.class);
    }

}
