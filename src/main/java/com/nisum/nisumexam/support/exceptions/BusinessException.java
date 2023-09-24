package com.nisum.nisumexam.support.exceptions;

public class BusinessException extends RuntimeException {
  
  private String mensaje;
  
  
  public BusinessException(String message) {
    super(message);
    this.mensaje = message;
  }
  
  
  public String getMensaje() {
    return mensaje;
  }
  
  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }
}
