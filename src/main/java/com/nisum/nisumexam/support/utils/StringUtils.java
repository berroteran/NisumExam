package com.nisum.nisumexam.support.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class StringUtils {
  
  public static String entityToString(Object object) {
    ObjectMapper ow = new ObjectMapper();
    ow.registerModule(new JavaTimeModule());
    ow.writer().withDefaultPrettyPrinter();
    String json = "";
    try {
      json = ow.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      json = "[Exception: error parse to json]";
    }
    return json;
  }
  
  /**
   * @param variable
   * @return
   */
  public static boolean isNoData(String variable) {
    if (null == variable) {
      return true;
    }
    if (variable.isEmpty()) {
      return true;
    }
    return false;
  }
}
