package com.example.psp.service;

/**
 * Interface for interacting with acquirers.
 * Defines the method for processing a transaction on the acquirer side.
 */

public interface AcquirerService {

    String process(String cardNumber, String acquirer);

}
