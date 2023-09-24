package com.nisum.nisumexam.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Table(name = "phones")
public class Phone implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;
  
  @JsonIgnore
  @ManyToOne()
  @JoinColumn(name = "user_id")
  private User user;
  
  @NotEmpty
  private String number;
  
  @NotEmpty
  private String cityCode;
  
  @NotEmpty
  private String countryCode;
  
  public Phone(String number, String countryCode, String cityCode) {
    this.number = number;
    this.countryCode = countryCode;
    this.cityCode = cityCode;
  }
  
  public Phone() {
  }
  
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getNumber() {
    return number;
  }
  
  public void setNumber(String number) {
    this.number = number;
  }
  
  public String getCityCode() {
    return cityCode;
  }
  
  public void setCityCode(String citycode) {
    this.cityCode = citycode;
  }
  
  public String getCountryCode() {
    return countryCode;
  }
  
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
}
