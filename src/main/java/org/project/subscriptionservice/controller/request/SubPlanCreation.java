package org.project.subscriptionservice.controller.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SubPlanCreation {

    @NotNull
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Currency is required")
    @NotNull
    @Pattern(regexp = "^(USD|KHR|GBP|JPY|INR|CNY)$", message = "Invalided currency")
    private String currency;

    @NotNull(message = "Price is required")
    @Digits(fraction = 4, integer = 11)
    @Min(0)
    private double price;

    @NotNull
    @NotBlank(message = "Billing period is required")
    @Pattern(regexp = "^(MONTHLY|YEAR|WEEKLY|DAY)$", message = "Invalided plan")
    private String period;

    private String description;

}
