package com.nisum.nisumexam.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.nisum.nisumexam.persistence.User;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserCreatedDTO {
  
  private UUID          id;
  
  @JsonFormat(pattern="yyyy-MM-dd")
  private LocalDateTime created;
  
  @JsonFormat(pattern="yyyy-MM-dd")
  private LocalDateTime modified;
  
  @JsonFormat(pattern="yyyy-MM-dd")
  private LocalDateTime lastLogin;
  private String        token;
  private boolean       active;
  
  public static UserCreatedDTO parse(User newUser) {
    UserCreatedDTO userResponse = new UserCreatedDTO();
    userResponse.setId(newUser.getId());
    userResponse.setCreated(newUser.getCreationDate());
    userResponse.setModified(newUser.getUpdateDate());
    userResponse.setLastLogin(newUser.getLastLoginDate());
    userResponse.setToken(newUser.getToken());
    userResponse.setActive(newUser.isActive());
    return userResponse;
  }
  
  public LocalDateTime getLastLogin() {
    return lastLogin;
  }
  
  public void setLastLogin(LocalDateTime lastLogin) {
    this.lastLogin = lastLogin;
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
  
  public LocalDateTime getModified() {
    return modified;
  }
  
  public void setModified(LocalDateTime modified) {
    this.modified = modified;
  }
  
  public LocalDateTime getCreated() {
    return created;
  }
  
  public void setCreated(LocalDateTime created) {
    this.created = created;
  }
  
  public UUID getId() {
    return id;
  }
  
  public void setId(UUID id) {
    this.id = id;
  }
  
}
