package org.project.subscriptionservice.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {

    @NotNull
    @NotBlank(message = "Email or Username is required")
    private String username;

    @NotNull
    @NotBlank(message = "Password is required")
    private String password;

    @NotNull
    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "UUID is required")
    @NotNull
    private String uuid;
}
