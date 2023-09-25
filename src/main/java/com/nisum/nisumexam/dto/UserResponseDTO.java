package com.nisum.nisumexam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nisum.nisumexam.persistence.Phone;
import com.nisum.nisumexam.persistence.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record UserResponseDTO(UUID id, String name, String email, List<Phone> phones,
                              @JsonFormat(pattern="yyyy-MM-dd")LocalDateTime creationDate,
                              @JsonFormat(pattern="yyyy-MM-dd")LocalDateTime updateDate,
                              @JsonFormat(pattern="yyyy-MM-dd")LocalDateTime lastLoginDate,
                              String token, boolean active) {
  
  public UserResponseDTO(User user) {
    this(user.getId(), user.getName(), user.getEmail(), user.getPhones(), user.getCreationDate(), user.getUpdateDate(), user.getLastLoginDate(), user.getToken(), user.isActive());
  }
  
  public static List<UserResponseDTO> parseList(List<User> userlist) {
    List<UserResponseDTO> list = new ArrayList<>();
    for (User user : userlist) {
      list.add(parse(user));
    }
    return list;
  }
  
  public static UserResponseDTO parse(User newUser) {
    UserResponseDTO userResponse = new UserResponseDTO(newUser);
    return userResponse;
  }
  
  
}
