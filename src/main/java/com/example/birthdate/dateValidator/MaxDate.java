package com.example.birthdate.dateValidator;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Past;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxDateValidator.class)
public @interface MaxDate {
    String message() default "Date must not be after {value}";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    String value() default "current date";

}
