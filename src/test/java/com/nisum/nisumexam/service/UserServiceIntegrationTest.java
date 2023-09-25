package com.nisum.nisumexam.service;

import com.nisum.nisumexam.persistence.User;
import com.nisum.nisumexam.repository.RolRepository;
import com.nisum.nisumexam.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserServiceIntegrationTest {
  
  private static final String PASSWORD = "hunter2";
  private static final String USER     = "juan@rodriguez.org";
  private static final String TOKEN    = "123213546564645454564adfasdfasdf654asdfasdfasdf798asdf";
  
  @InjectMocks
  private UserService userService;
  
  @Mock
  private UserRepository userRepository;
  
  @Mock
  private RolRepository roleRepository;
  
  @Mock
  private PasswordEncoder passwordEncoder;
  
  @BeforeEach
  void init() {
    userService = new UserService(userRepository, roleRepository, passwordEncoder) {
    };
  }
  
  
  private User getDummieUserEntity() {
    User userEntity = new User();
    userEntity.setName("Roberto");
    userEntity.setPassword("roberto");
    userEntity.setEmail(USER);
    userEntity.setId(UUID.randomUUID());
    userEntity.setActive(true);
    userEntity.setCreationDate(LocalDateTime.now());
    userEntity.setLastLoginDate(LocalDateTime.now());
    
    return userEntity;
  }
}
