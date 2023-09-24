package com.nisum.nisumexam.dto;


import com.nisum.nisumexam.persistence.Phone;
import com.nisum.nisumexam.persistence.User;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;


/**
 * Clase DTO para la creacion de usuarios
 */
public class UserRequest {
  
  @NotEmpty
  private String name;
  @NotEmpty
  private String email;
  @NotEmpty
  private String password;
  
  private List<Phone> phones;
  
  public User toEntity() {
    User user = new User();
    user.setName(this.getName());
    user.setEmail(this.getEmail());
    user.setPassword(this.getPassword());
    user.setPhones(this.getPhones());
    return user;
  }
  
  public User toEntity(UUID id) {
    User user = new User();
    user.setId(id);
    user.setName(this.getName());
    user.setEmail(this.getEmail());
    user.setPassword(this.getPassword());
    user.setPhones(this.getPhones());
    return user;
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
  
  public String getPassword() {
    return password;
  }
  
  public List<Phone> getPhones() {
    return phones;
  }
  
  public void setPhones(List<Phone> phones) {
    this.phones = phones;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
}
