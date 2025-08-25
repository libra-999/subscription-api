package org.project.subscriptionservice.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SubPlanUpdated {

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^(USD|KHR|GBP|JPY|INR|CNY)$", message = "Invalided currency")
    private String currency;

    @Min(0)
    private double price;

    @NotNull
    @Pattern(regexp = "^(MONTHLY|YEAR|WEEK|DAY)$", message = "Invalided plan")
    private String period;

    private String description;
}
