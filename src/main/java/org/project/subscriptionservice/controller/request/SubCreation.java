package org.project.subscriptionservice.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubCreation {

    @NotNull(message = "User object is required")
    private SubUserRequest user;

    @NotNull(message = "Payment is required")
    private PaymentCreation payment;

    @NotNull
    @NotBlank(message = "Plan Reference is required")
    private Sub_SubPlanRefRequest plan;

    private Boolean autoRenew;

    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @NotNull(message = "Date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;
}
