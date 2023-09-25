package com.nisum.nisumexam.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.nisumexam.persistence.User;
import com.nisum.nisumexam.support.utils.StringUtils;
import org.junit.jupiter.api.Test;

class StringUtilsTest {
  
  @Test
  void entityToString() {
    User userTest = new User();
    userTest.setName("Juan");
    userTest.setEmail("email@server.com");
    String json = StringUtils.entityToString(userTest);
    
    assertTrue(isValid(json));
  }
  
  public boolean isValid(String json) {
    ObjectMapper mapper = new ObjectMapper().enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
    try {
      mapper.readTree(json);
    } catch (JacksonException e) {
      return false;
    }
    return true;
  }
  
  @Test
  void isNoData() {
    assertTrue(StringUtils.isNoData(null));
    assertTrue(StringUtils.isNoData(""));
    assertFalse(StringUtils.isNoData("x"));
  }
}