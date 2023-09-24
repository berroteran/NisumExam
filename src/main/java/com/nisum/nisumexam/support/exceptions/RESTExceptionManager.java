package com.nisum.nisumexam.support.exceptions;

import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RESTExceptionManager {
  
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  InfoErrorMessageResponse handleNotFoundException(NotFoundException e) {
    return new InfoErrorMessageResponse(e);
  }
  
  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  InfoErrorMessageResponse handleBusinessException(BusinessException e) {
    return new InfoErrorMessageResponse(e);
  }
  
  
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  InfoErrorMessageResponse handleIllegalArgumentException(IllegalArgumentException e) {
    return new InfoErrorMessageResponse(e);
  }
  
  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  InfoErrorMessageResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
    return new InfoErrorMessageResponse(e);
  }
  
  @ExceptionHandler(SQLException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  InfoErrorMessageResponse handleSQLException(SQLException e) {
    return new InfoErrorMessageResponse(e);
  }
  
}
