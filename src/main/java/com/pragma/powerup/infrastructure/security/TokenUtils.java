package com.pragma.powerup.infrastructure.security;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pragma.powerup.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

    private static final String ACCESS_TOKEN_SECRET = "1KFB9tv3d4P5CF7jF7MZykEegGsTpYjQJKdGCjMJGtVTRBu8g2bL6jCxY7iptQfP";
    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 3_600L;

    public String getToken(User user) {
        return createToken(user.getName(), user.getEmail());
    }

    public static String createToken(String name, String email)
    {

        Long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;

        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extraDate = new HashMap<>();
        extraDate.put("nombre", name);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extraDate)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();

    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); //Proceso inverso a cuando creamos el token

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        }catch (JwtException e){
            return null;
        }
    }

}
