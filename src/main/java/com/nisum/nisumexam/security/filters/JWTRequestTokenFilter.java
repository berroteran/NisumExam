package com.nisum.nisumexam.security.filters;


import com.nisum.nisumexam.security.JWTUtilService;
import com.nisum.nisumexam.security.UserDetailSecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTRequestTokenFilter extends OncePerRequestFilter {
  
  private static final String BEARER = "Bearer";
  
  private final UserDetailSecurityService userDetailsService;
  private final JWTUtilService            jwtUtilService;
  
  public JWTRequestTokenFilter(UserDetailSecurityService userDetailsService, JWTUtilService jwtUtilService) {
    this.userDetailsService = userDetailsService;
    this.jwtUtilService     = jwtUtilService;
  }
  
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    
    String           authorizationHeader = request.getHeader("Authorization");
    Optional<String> jwt                 = null;
    boolean          logout              = getLogOut(request);
    
    jwt = getBearerToken(authorizationHeader);
    if (jwt.isPresent()) {
      String username = jwtUtilService.extractUsername(jwt.get());
      if (logout) {
        userDetailsService.logout(username);
      } else {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username, jwt.get());
        if (userDetails.isAccountNonExpired()) {
          if (userDetails.isEnabled()) {
            if (!jwtUtilService.isValidToken(jwt.get(), userDetails)) {
              throw new RuntimeException("Token expired");
            }
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
              
              UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
              authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
          }
        }
      }
    }
    
    filterChain.doFilter(request, response);
  }
  
  private Boolean getLogOut(HttpServletRequest request) {
    return request.getRequestURI().equals("/api/v1/Auth/logout");
  }
  
  private Optional<String> getBearerToken(String headerVal) {
    if (headerVal != null && headerVal.startsWith(BEARER)) {
      return Optional.of(headerVal.replace(BEARER, "").trim());
    }
    return Optional.empty();
  }
  
}
