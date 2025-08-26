package org.project.subscriptionservice.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubCreation {

    @NotNull(message = "User object is required")
    private SubUserRequest user;

    @NotNull(message = "Payment is required")
    private PaymentCreation payment;

    @NotNull(message = "Plan Reference is required")
    private Sub_SubPlanRefRequest plan;

    private Boolean autoRenew;
}
