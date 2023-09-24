package com.nisum.nisumexam.dto;

import com.nisum.nisumexam.persistence.Phone;
import com.nisum.nisumexam.persistence.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record UserResponseDTO(UUID id, String name, String email, List<Phone> phones, LocalDateTime creationDate, LocalDateTime updateDate, LocalDateTime lastLoginDate, String token, boolean active) {
  
  
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
  
  public static Optional<User> allToList(List<User> all) {
    return null;
  }
  
  private List<Phone> getPhonesEntites(User newUser) {
    List<Phone> lp = new ArrayList<>();
    if (this.phones().size() > 0) {
      for (Phone p : this.phones()) {
        Phone np = new Phone();
        np.setCountryCode(p.getCountryCode());
        np.setUser(newUser);
        np.setCityCode(p.getCityCode());
        np.setNumber(p.getNumber());
        lp.add(np);
      }
    }
    return lp;
  }
}
