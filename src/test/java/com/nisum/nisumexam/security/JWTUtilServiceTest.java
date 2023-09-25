package com.nisum.nisumexam.security;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.core.userdetails.User.withUsername;

import com.nisum.nisumexam.persistence.User;
import com.nisum.nisumexam.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JWTUtilServiceTest {
  
  @Mock
  private JWTUtilService        jwtUtilService;
  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private UserRepository        userRepository;
  
  @Value("${security.jwt.token.secret-key}")
  private String JWT_SECRET_KEY;
  @Value("${security.jwt.token.expiration}")
  private long   JWT_ExpirationTime = 10_800_000;
  
  @BeforeEach
  void setUp() {
    jwtUtilService = new JWTUtilService(JWT_SECRET_KEY, JWT_ExpirationTime) {
    };
  }
  
  @Test
  void generateToken() {
    String token = jwtUtilService.generateToken(getUserDetailsDummy());
    assertTrue(jwtUtilService.isValidToken(token, getUserDetailsDummy()));
  }
  
  public UserDetails getUserDetailsDummy() {
    return withUsername(getValidUserDummy().get().getEmail()).password(getValidUserDummy().get().getPassword()).authorities(getValidUserDummy().get().getRoles())
        .disabled(getValidUserDummy().get().isActive()).accountExpired(false).accountLocked(getValidUserDummy().get().isLocked()).credentialsExpired(getValidUserDummy().get().isExpired())
        .disabled(false).build();
  }
  
  private Optional<User> getValidUserDummy() {
    User userEntity = new User();
    userEntity.setEmail("uservalid@server.com");
    userEntity.setId(UUID.randomUUID());
    userEntity.setActive(true);
    userEntity.setName("Roberto");
    userEntity.setPassword("$2a$12$UJgVmRVKUqeOxruQOE8Lw.BbAYOBTG0U6NOTQCsH3/5tjxkTLnCLm"); //pass1
    userEntity.setCreationDate(LocalDateTime.now());
    userEntity.setLastLoginDate(LocalDateTime.now());
    userEntity.setToken("");
    
    return Optional.of(userEntity);
  }
  
  @Test
  void getEmailSigned() {
  }
  
  @Test
  void isValidToken() {
  }
}