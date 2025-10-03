package com.example.psp.service;

import com.example.psp.model.PaymentRequest;
import com.example.psp.model.PaymentResponse;

/**
 * Service interface for processing payments.
 * Defines methods for creating transactions.
 */

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);

}
