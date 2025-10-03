package com.example.psp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transaction entity in the system.
 * Stores identifier, original request, and processing status.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String transactionId;
    private PaymentRequest request;
    private String status;

}
