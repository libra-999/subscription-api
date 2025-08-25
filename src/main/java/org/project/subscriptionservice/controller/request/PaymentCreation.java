package org.project.subscriptionservice.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PaymentCreation {

    @NotNull
    @NotBlank(message = "Payment Method is required")
    @Pattern(regexp = "^(CASH|MASTER_CARD|PAYPAL|VISA|CRYPTO)$", message = "Invalided method")
    private String paymentMethod;

    @NotNull
    private AddressRequest billingAddress;

}
