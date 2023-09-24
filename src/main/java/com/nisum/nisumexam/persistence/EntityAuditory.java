package com.nisum.nisumexam.persistence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nisum.nisumexam.support.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"date_creation", "date_modified"}, allowGetters = true)
public class EntityAuditory {
  
  static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  
  @Column(name = "date_creation", nullable = true, updatable = false)
  @CreatedDate
  private LocalDateTime creationDate = DateUtils.getCurrenLocaltDateTime();
  
  @Column(name = "date_modified", nullable = true)
  @LastModifiedDate
  private LocalDateTime updateDate;
  
  private String createby;
  private String modifiedBy;
  
  ///////
  
  public LocalDateTime getUpdateDate() {
    return updateDate;
  }
  
  public String getModifiedBy() {
    return modifiedBy;
  }
  
  public void setModifiedBy(String mod_by) {
    this.modifiedBy = mod_by;
  }
  
  public String getDataPersistent() {
    return getCreationDate().format(dateTimeFormatter) + " by: " + getCreateby();
  }
  
  public LocalDateTime getCreationDate() {
    return creationDate == null ?  DateUtils.getCurrenLocaltDateTime() : creationDate;
  }
  
  public String getCreateby() {
    return createby;
  }
  
  public void setCreateby(String create_By) {
    this.createby = create_By;
  }
  
  public void setCreationDate(LocalDateTime creation_date) {
    this.creationDate = creation_date;
  }
  
  @PrePersist
  protected void onCreate() {
    creationDate = DateUtils.getCurrenLocaltDateTime();
    updateDate = DateUtils.getCurrenLocaltDateTime();
    createby = (getCurrentAuditor() == null ? "anonymous" : getCurrentAuditor().getEmail());
  }
  
  public User getCurrentAuditor() {
    return null;
  }
  
  @PreUpdate
  protected void onUpdate() {
    updateDate = DateUtils.getCurrenLocaltDateTime();
    modifiedBy = (getCurrentAuditor() == null ? "anonymous" : getCurrentAuditor().getEmail());
  }
  
  
}
