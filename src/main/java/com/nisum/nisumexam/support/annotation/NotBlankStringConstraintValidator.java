package com.nisum.nisumexam.support.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankStringConstraintValidator implements ConstraintValidator<NotEmptyBlankString, String> {
  
  @Override
  public void initialize(NotEmptyBlankString constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }
  
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value != null && !value.trim().isEmpty();
  }
}
