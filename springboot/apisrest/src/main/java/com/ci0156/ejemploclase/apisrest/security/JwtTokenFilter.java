package com.ci0156.ejemploclase.apisrest.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String HEADER_PREFIX = "Bearer ";

  private final JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    String token = this.getToken(httpServletRequest);
    try {
      if (token != null && jwtTokenProvider.validateToken(token)) {
        // TODO: falta revisar si el token esta en la base de datos y es valido
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (Exception ex) {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private String getToken(HttpServletRequest req){
    String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
    if(bearerToken != null && bearerToken.startsWith(HEADER_PREFIX)){
      return bearerToken.replace(HEADER_PREFIX, "");
    }
    return null;
  }
}
