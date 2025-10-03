package com.example.psp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * Mock implementation of the acquirer service.
 * Simulates the payment provider: decides Approved/Denied based on the card number.
 */
@Service
public class AcquirerServiceMockImpl implements AcquirerService {

    private static final Logger log = LoggerFactory.getLogger(AcquirerServiceMockImpl.class);

    /**
     * Decides to approve (even last digit of card number) or deny (odd last digit) the transaction.
     * @param cardNumber
     * @param acquirer
     * @return
     */
    @Override
    public String process(String cardNumber, String acquirer) {
        int lastDigit = Character.getNumericValue(cardNumber.charAt(cardNumber.length() - 1));
        String r = (lastDigit % 2 == 0) ? "Approved" : "Denied";
        log.debug("Card number (masked): ****{}, with acquirer {} is {}",
                cardNumber.substring(cardNumber.length() - 4), acquirer, r);
        return r;
    }
}
