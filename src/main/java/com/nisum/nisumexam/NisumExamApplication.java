package com.nisum.nisumexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
public class NisumExamApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(NisumExamApplication.class, args);
  }
  
}
