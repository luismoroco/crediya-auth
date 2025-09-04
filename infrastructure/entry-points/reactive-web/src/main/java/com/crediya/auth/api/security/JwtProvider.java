package com.crediya.auth.api.security;

import com.crediya.auth.model.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider implements com.crediya.auth.model.user.gateways.JwtProvider {

  @Value("${jwt.secret}")
  private String secret;
  @Value("${jwt.expiration}")
  private Integer expiration;

  public Claims getClaims(String token) {
    return Jwts.parser()
      .verifyWith(getKey(secret))
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  private SecretKey getKey(String secret) {
    byte[] secretBytes = Decoders.BASE64URL.decode(secret);
    return Keys.hmacShaKeyFor(secretBytes);
  }

  @Override
  public String generate(User user) {
    return Jwts.builder()
      .setSubject(user.getEmail())
      .claim("userId", user.getUserId())
      .claim("role", user.getUserRole().name())
      .claim("identityCardNumber", user.getIdentityCardNumber())
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + expiration))
      .signWith(getKey(secret), SignatureAlgorithm.HS256)
      .compact();
  }
}
