package com.example.psp.service;

import com.example.psp.model.PaymentRequest;
import com.example.psp.model.PaymentResponse;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);

}
