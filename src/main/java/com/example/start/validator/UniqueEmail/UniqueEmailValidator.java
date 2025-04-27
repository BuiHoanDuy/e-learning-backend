package com.example.start.validator.UniqueEmail;

import com.example.start.repository.UserRepository;
import com.example.start.validator.Dob.DobConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmailConstraint, String> {
    @Override
    public void initialize(UniqueEmailConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true; // để @NotBlank lo vụ null
        }
        return !userRepository.existsByEmail(email);
    }
}
