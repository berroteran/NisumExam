package com.nisum.nisumexam.security;

import static org.springframework.security.core.userdetails.User.withUsername;

import com.nisum.nisumexam.persistence.User;
import com.nisum.nisumexam.repository.UserRepository;
import com.nisum.nisumexam.support.exceptions.BusinessException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailSecurityService implements UserDetailsService {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailSecurityService.class);
  
  private final UserRepository userRepository;
  
  public UserDetailSecurityService(UserRepository userService) {
    this.userRepository = userService;
  }
  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).orElse(null);
    return fillUserDetail(user);
  }
  
  private UserDetails fillUserDetail(User user) {
    return withUsername(user.getEmail()).password(user.getPassword()).authorities(user.getRoles()).disabled(user.isActive()).accountExpired(false).accountLocked(user.isLocked())
        .credentialsExpired(user.isExpired()).disabled(false).build();
  }
  
  public UserDetails loadUserByUsername(String email, String jwt) throws UsernameNotFoundException {
    if (null == jwt) {
      throw new BusinessException("JWT no recuperado");
    }
    User user = findByEmail(email).orElse(null);
    if (jwt.equals(user.getToken())) {
      return fillUserDetail(user);
    } else {
      throw new BusinessException("Token no valid");
    }
  }
  
  public Optional<User> findByEmail(String email) {
    Optional<User> userFinded = userRepository.findByEmail(email);
    if (null == userFinded) {
      new UsernameNotFoundException(String.format("User with email %s does not exist", email));
    }
    return userFinded;
  }
  
  public void logout(String username) {
    User user = findByEmail(username).orElse(null);
    
    user.setExpired(true);
    user.setToken(null);
    userRepository.save(user);
  }
}

