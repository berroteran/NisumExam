package com.nisum.nisumexam.controller;


import com.nisum.nisumexam.dto.UserCreated;
import com.nisum.nisumexam.dto.UserRequest;
import com.nisum.nisumexam.dto.UserResponse;
import com.nisum.nisumexam.service.UserService;
import com.nisum.nisumexam.support.exceptions.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
  
  final private static Logger LOG = LoggerFactory.getLogger(UserController.class);
  
  private final UserService userService;
  
  public UserController(UserService userService) {
    this.userService = userService;
  }
  
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<UserResponse> findAllUser() {
    return userService.showAllUser();
  }
  
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public UserResponse findUserById(@NotNull @PathVariable UUID id) {
    LOG.info("call get user by ID.  uuid:  {}", id);
    UserResponse userResponse = userService.findDTOById(id).orElseThrow(() -> new NotFoundException(UserResponse.class, id));
    return userResponse;
  }
  
  @GetMapping(value = "email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public UserResponse findByEmail(@NotNull @PathVariable String email) {
    return userService.findByEmail(email) .orElseThrow(() -> new NotFoundException(UserResponse.class, "Email", email));
  }
  
  @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserCreated> createUser(@Valid @NotNull @RequestBody UserRequest newuser) {
    LOG.info("Call create user json {}", newuser);
    UserCreated newEntity = userService.createUser(newuser);
    return new ResponseEntity<>(newEntity, HttpStatus.CREATED);
  }
  
  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity udpateUser(@Valid @NotNull @PathVariable UUID id, @RequestBody UserRequest userdata) {
    // 200 update
    // 201, create nuevo
    // 204 no content, no find
    // 209 conflict
    userService.updateUser( userdata, id );
    return ResponseEntity.ok("User updated");
  }
  
  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity delete(@Valid @NotNull @PathVariable UUID id) {
    userService.deleteUser( id);
    return ResponseEntity.ok("User deleted");
  }
  
}