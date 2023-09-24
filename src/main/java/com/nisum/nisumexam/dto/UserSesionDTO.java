package com.nisum.nisumexam.dto;

import com.nisum.nisumexam.persistence.User;
import java.util.UUID;

public class UserSesionDTO {
  
  private UUID    id;
  private String  name;
  private String  email;
  private String  token;
  private boolean active;
  
  
  public static UserSesionDTO parse(User user) {
    UserSesionDTO userSesion = new UserSesionDTO();
    userSesion.setId(user.getId());
    userSesion.setName(user.getName());
    userSesion.setEmail(user.getEmail());
    userSesion.setToken(user.getToken());
    userSesion.setActive(user.isActive());
    
    return userSesion;
  }
  
  public boolean isActive() {
    return active;
  }
  
  public void setActive(boolean active) {
    this.active = active;
  }
  
  public String getToken() {
    return token;
  }
  
  public void setToken(String token) {
    this.token = token;
  }
  
  public UUID getId() {
    return id;
  }
  
  public void setId(UUID id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
}
