package com.nisum.nisumexam.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nisum.nisumexam.dto.LoginDTO;
import com.nisum.nisumexam.dto.UserSesionDTO;
import com.nisum.nisumexam.persistence.User;
import com.nisum.nisumexam.repository.UserRepository;
import com.nisum.nisumexam.security.JWTUtilService;
import com.nisum.nisumexam.security.UserDetailSecurityService;
import com.nisum.nisumexam.support.exceptions.BusinessException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class SessionServiceTest {
  
  private static final String                    JWT_TOKEN          = "eyJhbGciOiJIUzI1NiJ9.eyJyb2wiOnsiaWQiOjIsInJvbGVOYW1lIjoiUk9MRV9TVEFOREFSRCIsImRlc2NyaXB0aW9uIjoiVVNFUiIsImF1dGhvcml0eSI6IlJPTEVfU1RBTkRBUkQifSwic3ViIjoianVhbkByb2RyaWd1ZXoub3JnIiwiaWF0IjoxNjkyMTU4MDY5LCJleHAiOjE2OTIxNjg4Njl9.n3gFu0OHdexEv3XrkaUtAnF_UggI5PD4TZTWxXZRuyk";
  @InjectMocks
  private SessionService            sesionService;
  @InjectMocks
  private UserDetailSecurityService userDetailSecurityService;
  @Mock
  private JWTUtilService            jwtUtilService;
  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private UserRepository        userRepository;
  @Value("${security.jwt.token.secret-key}")
  private String                JWT_SECRET_KEY     = "secretKeyFor(123456789)Dummy*Dummy;";
  @Value("${security.jwt.token.expiration}")
  private              long                      JWT_ExpirationTime = 10_800_000;
  
  @BeforeEach
  void setUp() {
    jwtUtilService = new JWTUtilService(JWT_SECRET_KEY, JWT_ExpirationTime) {
      @Override
      public String generateToken(UserDetails userDetails) {
        return JWT_TOKEN;
      }
    };
    
    sesionService = new SessionService(authenticationManager, userDetailSecurityService, jwtUtilService, userRepository);
  }
  
  @AfterEach
  void tearDown() {
  }
  
  @Test
  void testSignin_whenUserIsFound_shouldReturnToken() {
    //pre
    Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(getValidUserDummy());
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(getValidUserDummy().get());
    
    LoginDTO      validLogin  = new LoginDTO("uservalid@server.com", "pass1");
    UserSesionDTO validSesion = sesionService.signin(validLogin);
    
    assertNotNull(validSesion);
    assertTrue(validSesion.getId().toString().length() > 5);
    assertTrue(validSesion.getName().length() > 5);
    assertTrue(validSesion.getEmail().length() > 5);
    assertTrue(validSesion.getToken().length() > 5);
    assertFalse(validSesion.getToken().isEmpty());
    assertSame(JWT_TOKEN, validSesion.getToken());
  }
  
  private Optional<User> getValidUserDummy() {
    User userEntity = new User();
    userEntity.setEmail("uservalid@server.com");
    userEntity.setId(UUID.randomUUID());
    userEntity.setActive(true);
    userEntity.setName("Roberto");
    userEntity.setPassword("$2a$12$UJgVmRVKUqeOxruQOE8Lw.BbAYOBTG0U6NOTQCsH3/5tjxkTLnCLm");
    userEntity.setCreationDate(LocalDateTime.now());
    userEntity.setLastLoginDate(LocalDateTime.now());
    userEntity.setToken(JWT_TOKEN);
    
    return Optional.of(userEntity);
  }
  

  
  @Test
  void testSignin_when_User_Is_Not_Found_should_Return_Exception() {
    LoginDTO inValidUser = new LoginDTO("usernovalid@server.com", "nopass");
    
    assertThrows(BusinessException.class, () -> sesionService.signin(inValidUser));
  }
  

}