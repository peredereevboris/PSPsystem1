package com.example.psp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for API response to a payment request.
 * Returns transactionId, status, and message.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String transactionId;
    private String status;
    private String message;

}
