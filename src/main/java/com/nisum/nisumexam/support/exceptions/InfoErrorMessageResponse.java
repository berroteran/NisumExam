package com.nisum.nisumexam.support.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public record InfoErrorMessageResponse(Exception exception) {
  
  private static final String NULL_OBJECT_MESSAGE = "Null Object";
  
  @JsonValue
  public Map<String, Object> asMap() {
    String message = Optional.of(exception).map(this::getErrorMessage).orElse(NULL_OBJECT_MESSAGE);
    return Map.of("mensaje", message);
  }
  
  private String getErrorMessage(Exception e) {
    if (e instanceof SQLException) {
      String msg       = e.getMessage();
      int    errorCode = ((SQLException) e).getErrorCode();
      if (errorCode == 23505 && msg.indexOf("Unique index or primary key violation") >= 0) {
        return "This email address already belongs to a registered User";
      } else {
        return String.format("Error Code: %s | Cause: %s", errorCode, msg);
      }
    } else {
      return e.getMessage();
    }
  }
}
