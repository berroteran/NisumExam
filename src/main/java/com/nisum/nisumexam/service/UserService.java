package com.nisum.nisumexam.service;

import com.nisum.nisumexam.dto.UserCreatedDTO;
import com.nisum.nisumexam.dto.UserRequestDTO;
import com.nisum.nisumexam.dto.UserResponseDTO;
import com.nisum.nisumexam.persistence.User;
import com.nisum.nisumexam.repository.RolRepository;
import com.nisum.nisumexam.repository.UserRepository;
import com.nisum.nisumexam.support.exceptions.BusinessException;
import com.nisum.nisumexam.support.exceptions.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  private static final Logger          LOGGER = Logger.getLogger(UserService.class.getName());
  final private        UserRepository  userRepository;
  private final        RolRepository   roleRepository;
  private final        PasswordEncoder passwordEncoder;
  
  @Value("${nisum.configuration.user.email}")
  private String EMAIL_REGEX;
  @Value("${nisum.configuration.user.password}")
  private String PASSWORD_REGEX;
  
  public UserService(UserRepository userRepository, RolRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.userRepository  = userRepository;
    this.roleRepository  = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }
  
  public Optional<UserResponseDTO> findDTOById(UUID id) {
    User            user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
    UserResponseDTO dto  = new UserResponseDTO(user);
    return Optional.of(dto);
  }
  
  public UserCreatedDTO createUser(UserRequestDTO newuser) {
    validUser(newuser);
    if (newuser.getPhones().isEmpty()) {
      throw new BusinessException("A new user require at least one phone.");
    }
    newuser.setPassword(encodePass(newuser.getPassword()));
    return UserCreatedDTO.parse(userRepository.save(newuser.toEntity()));
  }
  
  private void validUser(UserRequestDTO user) {
    if (user.getName() == null || user.getName().equals("")) {
      throw new BusinessException("The name is invalid");
    }
    
    if (!isValidPassword(user.getPassword())) {
      throw new BusinessException("The password is not valid requirement");
    }
    
    if (!isValidEmail(user.getEmail())) {
      throw new BusinessException("The email is not invalid format");
    }
  }
  
  private String encodePass(String password) {
    return passwordEncoder.encode(password);
  }
  
  private boolean isValidPassword(String password) {
    LOGGER.info("Validating password, REGEX: ".concat(PASSWORD_REGEX).concat(" , Pass:").concat(password));
    return password.matches(PASSWORD_REGEX);
  }
  
  private boolean isValidEmail(String email) {
    LOGGER.info("Validating email, REGEX: ".concat(EMAIL_REGEX).concat(" , email:").concat(email));
    return email.matches(EMAIL_REGEX);
  }
  
  public void updateUser(UserRequestDTO userdata, @Valid @NotNull UUID id) {
    validUser(userdata);
    User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
    user.setName(userdata.getName());
    user.setEmail(userdata.getEmail());
    user.setPhones(userdata.getPhones());
    userdata.setPassword(encodePass(userdata.getPassword()));
    userRepository.save(user);
  }
  
  public List<UserResponseDTO> showAllUser() {
    List<User>            users    = userRepository.findAll();
    List<UserResponseDTO> listUser = UserResponseDTO.parseList(users);
    return listUser;
  }
  
  public Optional<UserResponseDTO> findByEmail(String email) {
    Optional<User>            userFinded = userRepository.findByEmail(email);
    Optional<UserResponseDTO> userReturn = Optional.empty();
    if (userFinded.isPresent()) {
      userReturn = Optional.of(UserResponseDTO.parse(userFinded.get()));
    } else {
      new UsernameNotFoundException(String.format("User with email %s does not exist", email));
    }
    return userReturn;
  }
  
  public void save(User user) {
    userRepository.save(user);
  }
  
  public void deleteUser(@Valid @NotNull UUID id) {
    User tmpuser = userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
    userRepository.delete(tmpuser);
  }
}