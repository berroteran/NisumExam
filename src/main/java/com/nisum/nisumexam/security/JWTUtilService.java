package com.nisum.nisumexam.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@PropertySource(value = {"classpath:application.yml"})
public class JWTUtilService {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtilService.class);
  
  @Value("${security.jwt.token.secret-key}")
  private String JWT_SECRET_KEY;
  
  @Value("${security.jwt.token.expiration}")
  private long JWT_ExpirationTime;
  
  public JWTUtilService(@Value("${security.jwt.token.secret-key}") String secretKey,
                        @Value("${security.jwt.token.expiration}") long expirationTime) {
    LOGGER.info("Secret recuperado: {0}", secretKey);
    this.JWT_SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
    LOGGER.info("Secret coded: {0}" , this.JWT_SECRET_KEY);
    this.JWT_ExpirationTime = expirationTime;
  }
  
  public String generateToken(UserDetails userDetails) {
    if (null == userDetails) {
      return "";
    }
    Map<String, Object> claims = new HashMap<>();
    var rol = userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0);
    claims.put("rol", rol);
    return createToken(claims, userDetails.getUsername());
  }
  
  public String createToken(Map<String, Object> claims, String email) {
    LOGGER.info("****Secret: " + JWT_SECRET_KEY);
    if (null == claims) {
      return "";
    }
    Date dateTimeNOW = new Date();
    Date expiresAt = new Date(dateTimeNOW.getTime() + JWT_ExpirationTime);
    return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(dateTimeNOW).setExpiration(expiresAt).signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY).compact();
  }
  
  public boolean isValidToken(String jwtToken, UserDetails userDetails) {
    try {
      final String username = extractUsername(jwtToken);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
      
    } catch (MalformedJwtException e) {
      LOGGER.error("Invalid JWT token -> Message: {}", e);
    } catch (ExpiredJwtException e) {
      e.printStackTrace();
      LOGGER.error("Expired JWT token -> Message: {}", e);
    } catch (UnsupportedJwtException e) {
      e.printStackTrace();
      LOGGER.error("Unsupported JWT token -> Message: {}", e);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      LOGGER.error("JWT claims string is empty -> Message: {}", e);
    }
    return false;
  }
  
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }
  
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
  
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    return claimsResolver.apply(extractAllClaims(token));
  }
  
  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
  }
  
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
  
  public Optional<UserDetails> loadUserByJwtToken(String jwtToken) {
    
    LOGGER.info("eMAIL firmado: {} ", getEmailSigned(jwtToken));
    return Optional.of(withUsername(getEmailSigned(jwtToken)).authorities(new SimpleGrantedAuthority("STANDARD_USER")).password("") //token does not have password but field may not be empty
        .accountExpired(false).accountLocked(false).credentialsExpired(false).disabled(false).build());
  }
  
  /**
   * Get the email by the subject
   */
  public String getEmailSigned(String token) {
    return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
  }
}
