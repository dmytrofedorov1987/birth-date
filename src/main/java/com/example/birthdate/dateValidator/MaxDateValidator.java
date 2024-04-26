package com.example.birthdate.dateValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
public class MaxDateValidator implements ConstraintValidator<MaxDate, LocalDate> {
    private LocalDate maxDate;

    @Override
    public void initialize(MaxDate constraintAnnotation) {
        maxDate = LocalDate.now();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value.isBefore(maxDate);
    }
}