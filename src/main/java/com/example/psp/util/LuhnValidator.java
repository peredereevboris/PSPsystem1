package com.example.psp.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Custom validator for card number validation (Luhn + length 13–19 digits).
 */
public class LuhnValidator implements ConstraintValidator<ValidCardNumber, String> {

    public static final int CARD_NUMBER_MIN_LENGTH = 13;
    public static final int CARD_NUMBER_MAX_LENGTH = 19;

    @Override
    public boolean isValid(String cardNumber, ConstraintValidatorContext context) {
        if (cardNumber == null
                || cardNumber.length() < CARD_NUMBER_MIN_LENGTH
                ||  cardNumber.length() > CARD_NUMBER_MAX_LENGTH) return false;

        int sum = 0;
        boolean alternate = false;
        try {
            for (int i = cardNumber.length() - 1; i >= 0; i--) {
                int n = Integer.parseInt(cardNumber.substring(i, i + 1));
                if (alternate) {
                    n *= 2;
                    if (n > 9) {
                        n = (n % 10) + 1;
                    }
                }
                sum += n;
                alternate = !alternate;
            }
            return (sum % 10 == 0);
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}

