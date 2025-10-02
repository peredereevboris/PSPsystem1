package com.example.psp.controller;

import com.example.psp.model.PaymentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Valid credit card number which sum is even returns "Approved" status
     * @throws Exception
     */
    @Test
    void testCreatePaymentApproved() throws Exception {
        PaymentRequest request = new PaymentRequest(
                "4242424242424242", // If the sum is even -> Approved
                "12/25",
                "123",
                100.0,
                "USD",
                "M123"
        );

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Approved"))
                .andExpect(jsonPath("$.message").value("Processed by Acquirer A"))
                .andExpect(jsonPath("$.transactionId").exists());
    }

    /**
     * Valid credit card number which sum is odd returns "Denied" status
     * @throws Exception
     */
    @Test
    void testCreatePaymentDenied() throws Exception {
        PaymentRequest request = new PaymentRequest(
                "4111111111111111", // If the sum is odd -> Denied
                "12/25",
                "123",
                50.0,
                "USD",
                "M456"
        );

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Denied"))
                .andExpect(jsonPath("$.message").value("Processed by Acquirer B"))
                .andExpect(jsonPath("$.transactionId").exists());
    }

    /**
     * Credit card number has missed
     * @throws Exception
     */

    @Test
    void testCreatePaymentMissingCardNumber() throws Exception {
        PaymentRequest request = new PaymentRequest(
                "", // cardNumber is empty
                "12/25",
                "123",
                100.0,
                "USD",
                "M123"
        );

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("cardNumber"))
                .andExpect(jsonPath("$.errors[0].message").value("Invalid card number"));
    }

    /**
     * Credit card number has not passed Luhn's algorithm.
     * @throws Exception
     */

    @Test
    void testCreatePaymentIncorrectCardNumber() throws Exception {
        PaymentRequest request = new PaymentRequest(
                "4111111111111112", // cardNumber is empty
                "12/25",
                "123",
                100.0,
                "USD",
                "M123"
        );

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("cardNumber"))
                .andExpect(jsonPath("$.errors[0].message").value("Invalid card number"));
    }

    /**
     * Incorrect ExpiryDate
     * @throws Exception
     */

    @Test
    void testCreatePaymentIncorrectExpiryDate() throws Exception {
        PaymentRequest request = new PaymentRequest(
                "4242424242424242", // cardNumber is empty
                "12/251",
                "123",
                100.0,
                "USD",
                "M123"
        );

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("expiryDate"))
                .andExpect(jsonPath("$.errors[0].message").value("expiryDate must be in MM/YY or MM/YYYY format and month between 01 and 12"));
    }

    /**
     * Incorrect CVV
     * @throws Exception
     */

    @Test
    void testCreatePaymentIncorrectCVV() throws Exception {
        PaymentRequest request = new PaymentRequest(
                "4242424242424242", // cardNumber is empty
                "12/25",
                "",
                100.0,
                "USD",
                "M123"
        );

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("cvv"))
                .andExpect(jsonPath("$.errors[0].message").value("CVV must be 3 or 4 digits"));
    }

    /**
     * Incorrect Amount
     * @throws Exception
     */

    @Test
    void testCreatePaymentIncorrectAmount() throws Exception {
        PaymentRequest request = new PaymentRequest(
                "4242424242424242", // cardNumber is empty
                "12/25",
                "123",
                -100.0,
                "USD",
                "M123"
        );

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("amount"))
                .andExpect(jsonPath("$.errors[0].message").value("Amount must be positive"));
    }

    /**
     * Incorrect Currency
     * @throws Exception
     */

    @Test
    void testCreatePaymentIncorrectCurrency() throws Exception {
        PaymentRequest request = new PaymentRequest(
                "4242424242424242", // cardNumber is empty
                "12/25",
                "123",
                100.0,
                "",
                "M123"
        );

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("currency"))
                .andExpect(jsonPath("$.errors[0].message").value("Currency is required"));
    }

    /**
     * Incorrect Currency
     * @throws Exception
     */

    @Test
    void testCreatePaymentIncorrectMerchantId() throws Exception {
        PaymentRequest request = new PaymentRequest(
                "4242424242424242", // cardNumber is empty
                "12/25",
                "123",
                100.0,
                "USD",
                ""
        );

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("merchantId"))
                .andExpect(jsonPath("$.errors[0].message").value("Merchant ID is required"));
    }

}