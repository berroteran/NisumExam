package com.nisum.nisumexam.repository;

import com.nisum.nisumexam.persistence.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  
  @Query("select u from User u left join fetch u.roles r where u.email = :pEmail ")
  public Optional<User> findByEmail(String pEmail);
  
  
}
