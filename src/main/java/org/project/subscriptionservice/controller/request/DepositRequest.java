package org.project.subscriptionservice.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DepositRequest {

    @NotNull
    @Min(1)
    private double amount;

    @NotNull
    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^(USD|KHR|GBP|JPY|INR|CNY)$", message = "Invalided currency")
    private String currency;

    @NotNull
    @NotBlank(message = "Payment Method is required")
    @Pattern(regexp = "^(CASH|MASTER_CARD|PAYPAL|VISA|CRYPTO)$", message = "Invalided method")
    private String paymentMethod;
}
