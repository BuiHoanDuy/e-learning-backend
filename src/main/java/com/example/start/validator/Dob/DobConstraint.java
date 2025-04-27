package com.example.start.validator.Dob;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
        int min() default 0;

        int max() default 130;

        String message() default "Your age is not valid, age should be between 0 and 130";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
}
