package com.nisum.nisumexam.service;


import com.nisum.nisumexam.dto.Login;
import com.nisum.nisumexam.dto.UserSesion;
import com.nisum.nisumexam.persistence.User;
import com.nisum.nisumexam.repository.UserRepository;
import com.nisum.nisumexam.security.JWTUtilService;
import com.nisum.nisumexam.security.UserDetailSecurityService;
import com.nisum.nisumexam.support.exceptions.BusinessException;
import com.nisum.nisumexam.support.utils.DateUtils;
import com.nisum.nisumexam.support.utils.StringUtils;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
  
  private static final Logger LOGGER = Logger.getLogger(SessionService.class.getName());
  private final AuthenticationManager     authenticationManager;
  private final UserDetailSecurityService userDetailSecurityService;
  private final JWTUtilService jwtUtilService;
  private final UserRepository userRepository;
  
  
  public SessionService(AuthenticationManager authenticationManager, UserDetailSecurityService userDetailSecurityService, JWTUtilService jwtUtilService, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.userDetailSecurityService = userDetailSecurityService;
    this.jwtUtilService = jwtUtilService;
    
    this.userRepository = userRepository;
  }
  
  public UserSesion signin(Login login) throws BusinessException {
    UserSesion     userSesion;
    Optional<User> userFinded = userRepository.findByEmail(login.email());
    
    if (userFinded.isPresent()) {
        if (userFinded.get().isActive()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.email(), login.password()));
            final UserDetails userDetails = userDetailSecurityService.loadUserByUsername(login.email());
            if (!userDetails.isEnabled()) {
                throw new BusinessException("User no activo.");
            }
            String token = jwtUtilService.generateToken(userDetails);
            if (StringUtils.isNoData(token)) {
                throw new BusinessException("No se puede iniciar sesion, no se puedo generar el token");
            }
            //
            userFinded.get().setLastLoginDate(DateUtils.getCurrenLocaltDateTime());
            userFinded.get().setToken(token);
            userRepository.save(userFinded.get());
            //
            userSesion = UserSesion.parse(userFinded.get());
        } else {
            throw new BusinessException("El usuario no está activo");
        }
    } else {
      throw new BusinessException("Usuario no encontrado");
    }
    return userSesion;
  }
  
  
}
