package com.ci0156.ejemploclase.apisrest.security;

import com.ci0156.ejemploclase.apisrest.exception.BadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length:3600000}")
  private long validityInMilliseconds = 3600000; // 1h

  @Value("${security.jwt.token.refresh-expire-length:3600000}")
  private long refreshValidityInMilliseconds = 3600000; // 1h

  private final CustomUserDetails customUserDetails;

  public JwtTokenProvider(CustomUserDetails customUserDetails){
    this.customUserDetails = customUserDetails;
  }

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username, long userId){
    Claims claims = Jwts.claims().setId(String.valueOf(userId)).setSubject(username);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
      .setClaims(claims)
      .setIssuedAt(now)
      .setExpiration(validity)
      .signWith(SignatureAlgorithm.HS256, secretKey)
      .compact();
  }

  public String buildRefreshToken(){
    return UUID.randomUUID().toString();
  }

  public long getValidity(){
    return this.validityInMilliseconds;
  }

  public long getRefreshValidityInMilliseconds(){
    return this.refreshValidityInMilliseconds;
  }

  public Date getRefreshExpirationDate(){
    Date now = new Date();
    return new Date(now.getTime() + refreshValidityInMilliseconds);
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = customUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new BadRequestException("Expired or invalid JWT token");
    }
  }

}
