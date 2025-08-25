package org.project.subscriptionservice.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubUserRequest {

    @NotNull
    @NotBlank(message = "Username is required")
    private String username;

    @NotNull
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull
    @NotBlank(message = "Phone is required")
    private String phone;

}
