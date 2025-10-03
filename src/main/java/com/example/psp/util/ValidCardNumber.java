package com.example.psp.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validator for @ValidCardNumber annotation.
 */

@Documented
@Constraint(validatedBy = LuhnValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCardNumber {
    String message() default "Invalid card number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
