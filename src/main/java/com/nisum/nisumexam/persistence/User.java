package com.nisum.nisumexam.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User extends EntityAuditory implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  @ColumnDefault("random_uuid()")
  private UUID   id;
  private String name;
  
  @Column(unique = true, nullable = false)
  private String email;
  private String password;
  
  
  private LocalDateTime lastLoginDate  = LocalDateTime.now();
  private LocalDateTime lastPassChange = LocalDateTime.now();
  private Boolean       active         = true;
  
  @Column(length = 400)
  private String token;
  
  
  @OneToMany(mappedBy = "user", targetEntity = Phone.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Phone> phones;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<Role> roles;
  private Boolean    locked  = false;
  private Boolean    expired = false;
  
  
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
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public List<Phone> getPhones() {
    return phones;
  }
  
  public void setPhones(List<Phone> phoneList) {
    for (Phone p : phoneList) {
      p.setUser(this);
    }
    this.phones = phoneList;
  }
  
  public Boolean getActive() {
    return active;
  }
  
  public LocalDateTime getLastLoginDate() {
    return lastLoginDate == null ? this.getCreationDate() : lastLoginDate;
  }
  
  public void setLastLoginDate(final LocalDateTime lastLoginDate) {
    this.lastLoginDate = lastLoginDate;
  }
  
  public String getToken() {
    return token;
  }
  
  public void setToken(final String token) {
    this.token = token;
  }
  
  public User orElseThrow(final Object o) {
    return null;
  }
  
  public List<Role> getRoles() {
    if (this.roles == null) {
      roles = new ArrayList<>();
    }
    if (this.roles.size() == 0) {
      
      roles.add(createRoleUser());
    }
    return roles;
  }
  
  private Role createRoleUser() {
    Role rol = new Role();
    rol.setId(2);
    rol.setRoleName("ROLE_STANDARD");
    rol.setDescription("USER");
    return rol;
  }
  
  public void setRoles(final List<Role> roles) {
    this.roles = roles;
  }
  
  public LocalDateTime getLastPassChange() {
    return lastPassChange;
  }
  
  public void setLastPassChange(LocalDateTime lastPassChange) {
    this.lastPassChange = lastPassChange;
  }
  
  public boolean isActive() {
    return active != null && active;
  }
  
  public void setActive(final Boolean activo) {
    this.active = activo;
  }
  
  public void setActive(boolean active) {
    this.active = active;
  }
  
  public boolean isLocked() {
    return locked != null && locked;
  }
  
  public void setLocked(boolean locked) {
    this.locked = locked;
  }
  
  public boolean isExpired() {
    return expired != null && expired;
  }
  
  public void setExpired(boolean expired) {
    this.expired = expired;
  }
}