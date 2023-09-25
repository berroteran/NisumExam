package com.nisum.nisumexam.security;

import static org.springframework.security.config.Customizer.withDefaults;

import com.nisum.nisumexam.security.filters.JWTRequestTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  
  
  @Autowired
  private JWTRequestTokenFilter jwtTokenFilter;
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
  
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
  
  
  @Bean
  MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
    return new MvcRequestMatcher.Builder(introspector);
  }
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    
    http.authorizeRequests(
        authorize -> authorize.requestMatchers("/").permitAll()
            .requestMatchers("/swagger-resources/**'", "/swagger-ui/**", "/api/api-docs**", "/bus/v3/api-docs/**", "/v3/api-docs/**").permitAll()
            
            .requestMatchers("/h2-console/**").permitAll()
            
            .requestMatchers("/user/signup").permitAll()
            
            .requestMatchers("/Auth/signin**").permitAll().requestMatchers("/Auth/logout**").permitAll()
            
            .anyRequest().authenticated()).csrf(AbstractHttpConfigurer::disable).cors(withDefaults()).sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    
    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }
}
