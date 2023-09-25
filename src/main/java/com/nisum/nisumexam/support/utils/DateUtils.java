package com.nisum.nisumexam.support.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {
  
  final public static String MY_TIME_ZONE = "America/Santiago";
  final static DateTimeFormatter DATE_FORMATTER     = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  
  
  public static LocalDateTime getCurrenLocaltDateTime() {
    LocalDateTime currentDate = LocalDateTime.now(ZoneId.of(MY_TIME_ZONE));
    return currentDate;
  }
  
  public static String formatDateTime(LocalDateTime localDateTime) {
    return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT, Locale.ENGLISH).format(localDateTime);
  }

  
}
