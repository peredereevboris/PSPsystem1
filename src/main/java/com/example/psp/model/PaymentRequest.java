package com.example.psp.model;

import com.example.psp.util.ValidCardNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

/**
 * DTO for incoming payment request.
 * Contains card data, amount, currency, and merchantId.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    @ValidCardNumber
    private String cardNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])/(?:\\d{2}|\\d{4})$",
            message = "expiryDate must be in MM/YY or MM/YYYY format and month between 01 and 12")
    private String expiryDate;

    @Pattern(regexp = "^\\d{3,4}$", message = "CVV must be 3 or 4 digits")
    private String cvv;

    @Positive(message = "Amount must be positive")
    private double amount;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotBlank(message = "Merchant ID is required")
    private String merchantId;
}
