package org.project.subscriptionservice.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequest {

    @NotNull(message = "Street is required")
    private String street;

    @NotNull(message = "City is required")
    private String city;

    @NotNull
    @NotBlank(message = "State is required")
    private String state;

    @NotNull(message = "Zip code is required")
    private Integer zipCode;

    @NotBlank(message = "Zip code is required")
    @NotNull
    private String country;

}
