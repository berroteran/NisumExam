package com.nisum.nisumexam.support.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {
  
  final public static String MY_TIME_ZONE = "America/Santiago";
  
  public static LocalDateTime getCurrenLocaltDateTime() {
    LocalDateTime currentDate = LocalDateTime.now(ZoneId.of(MY_TIME_ZONE));
    return currentDate;
  }
  
}
