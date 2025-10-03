package com.example.psp.service;

import com.example.psp.model.PaymentRequest;
import com.example.psp.model.PaymentResponse;
import com.example.psp.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Implementation of the payment service.
 * Performs BIN-based routing, and acquirer processing.
 */

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final Map<String, Transaction> storage = new ConcurrentHashMap<>();
    private final AcquirerService acquirerService;

    public PaymentServiceImpl(AcquirerService acquirerService) {
        this.acquirerService = acquirerService;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {

        log.info("Processing payment for merchant: {}, amount: {} {}",
                request.getMerchantId(), request.getAmount(), request.getCurrency());
        log.debug("Card number (masked): ****{}", request.getCardNumber().substring(request.getCardNumber().length() - 4));

        String transactionId = UUID.randomUUID().toString();
        Transaction tx = new Transaction(transactionId, request, "Pending");
        // Store transaction with status "Pending"
        storage.put(transactionId, tx);

        log.info("For merchant: {}, created and stored transaction with id: {} and status: {}",
                request.getMerchantId(), transactionId, tx.getStatus());


        // BIN routing
        String bin = request.getCardNumber().substring(0, 6);
        int sum = bin.chars().map(Character::getNumericValue).sum();
        String acquirer = (sum % 2 == 0) ? "A" : "B";

        log.info("For transaction: {}, has been chosen acquirer : {}",
                transactionId, acquirer);

        // Acquirer service mock returns transaction status
        String status = acquirerService.process(request.getCardNumber(), acquirer);


        tx.setStatus(status);

        log.info("Transaction: {}, has status : {} and persisted in database",
                transactionId, status);

        return new PaymentResponse(transactionId, status, "Processed by Acquirer " + acquirer);
    }

}
