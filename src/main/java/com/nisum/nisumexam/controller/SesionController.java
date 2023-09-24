package com.nisum.nisumexam.controller;

import com.nisum.nisumexam.dto.LoginDTO;
import com.nisum.nisumexam.dto.UserSesionDTO;
import com.nisum.nisumexam.service.SessionService;
import com.nisum.nisumexam.support.utils.StringUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/Auth")
public class SesionController {
  
  final private static Logger         LOG = LoggerFactory.getLogger(SesionController.class);
  private final        SessionService sesionService;
  
  public SesionController(SessionService sesionService) {
    this.sesionService = sesionService;
  }
  
  /**
   * Inicia sesion en el sistema.
   *
   * @param login
   * @return
   */
  @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<?> signin(@RequestBody @Valid LoginDTO login) {
    LOG.info("JSON: {}", StringUtils.entityToString(login));
    UserSesionDTO user     = this.sesionService.signin(login);
    var           header   = new HttpHeaders();
    var           location = ServletUriComponentsBuilder.fromCurrentRequest().path("/v1/user/".concat(user.getId().toString())).buildAndExpand(1).toUri();
    header.setLocation(location);
    header.set(HttpHeaders.AUTHORIZATION, user.getToken());
    return new ResponseEntity<>(user, header, HttpStatus.OK);
  }
  
  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser() {
    return ResponseEntity.ok("Logout successful");
  }
  
  @GetMapping("/test")
  public ResponseEntity<?> test() {
    return ResponseEntity.ok("It's work!");
  }
  
}
