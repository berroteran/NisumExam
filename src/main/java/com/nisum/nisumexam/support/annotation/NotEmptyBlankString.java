package com.nisum.nisumexam.support.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotBlankStringConstraintValidator.class)
public @interface NotEmptyBlankString {
  
  String message() default "{not-blank-string.default}";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
}
