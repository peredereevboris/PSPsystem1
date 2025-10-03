package com.example.psp.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link LuhnValidator}.
 *
 * <p>These tests cover various scenarios for validating credit card numbers:
 * <ul>
 *   <li>Valid Visa and MasterCard numbers that should pass Luhn’s algorithm.</li>
 *   <li>Too short or too long numbers that must fail.</li>
 *   <li>Numbers containing non-digit characters (letters) that must fail.</li>
 *   <li>Numbers that do not pass Luhn checksum.</li>
 *   <li>Boundary cases: minimum length (13 digits) and maximum length (19 digits) with valid cards.</li>
 *   <li>Null input handling.</li>
 * </ul>
 *
 * <p>The test data includes well-known test card numbers from payment providers (e.g., Stripe, Visa, MasterCard).
 */

class LuhnValidatorTest {

    private LuhnValidator validator;

    @BeforeEach
    void setUp() {
        validator = new LuhnValidator();
    }

    @Test
    void validVisaCard_shouldPass() {
        // Stripe test card (valid according to Luhn)
        assertTrue(validator.isValid("4242424242424242", null));
    }

    @Test
    void validMasterCard_shouldPass() {
        assertTrue(validator.isValid("5555555555554444", null));
    }

    @Test
    void cardTooShort_shouldFail() {
        assertFalse(validator.isValid("1234567", null));
    }

    @Test
    void cardTooLong_shouldFail() {
        assertFalse(validator.isValid("12345678901234567890", null));
    }

    @Test
    void cardWithLetters_shouldFail() {
        assertFalse(validator.isValid("4242abcd42424242", null));
    }

    @Test
    void cardNotPassingLuhn_shouldFail() {
        // Change the last digit of a valid number
        assertFalse(validator.isValid("4242424242424243", null));
    }

    @Test
    void nullCard_shouldFail() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void cardWithMinLength13_shouldPassIfValid() {
        // 13-digit card that passes Luhn
        assertTrue(validator.isValid("4222222222222", null));
    }

    @Test
    void cardWithMaxLength19_shouldPassIfValid() {
        // 19-digit card that passes Luhn
        assertTrue(validator.isValid("4000000000000000006", null));
    }
}