package com.example.psp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String transactionId;
    private PaymentRequest request;
    private String status;

}
