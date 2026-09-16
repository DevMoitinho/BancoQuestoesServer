package com.DevMoitinho.bancoQuestoes.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String username){
        JwtBuilder jws = Jwts.builder()
                        .subject(username)
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 86400000))
                        .signWith(getKey());
        return jws.compact();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token){
         JwtParser parser = Jwts.parser()
                    .verifyWith(getKey())
                    .build();
        return parser.parseSignedClaims(token).getPayload();
        

    }

    public boolean isValidToken(String token, UserDetails userDetails){
        Claims claims = extractClaims(token);
        if(!claims.getSubject().equals(userDetails.getUsername())){
            return false;
        }
        return claims.getExpiration().after(new Date());

    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));
    }
}
