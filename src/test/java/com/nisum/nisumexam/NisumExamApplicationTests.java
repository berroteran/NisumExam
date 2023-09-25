package com.nisum.nisumexam;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NisumExamApplicationTests {
  
  @Value("${security.jwt.token.secret-key}")
  private String JWT_SECRET_KEY;
  
  @Value("${security.jwt.token.expiration}")
  private long JWT_ExpirationTime;
  
  @Test
  void contextLoads() {
    //Verificando lectura del parametro.
    assertTrue( JWT_SECRET_KEY.equals("TheZeroPhantonZerg2Go2SuperMall2SellBitCoins123"));
  }
  
}