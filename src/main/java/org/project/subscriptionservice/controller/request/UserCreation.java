package org.project.subscriptionservice.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreation {

    @NotNull
    @NotBlank(message = "Username is required")
    private String username;

    @NotNull
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z]+[0-9]+@gmail\\.com$", message = "Invalid email input")
    private String email;

    @NotNull
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^0[0-9]{8,9}$", message = "Invalid phone number input")
    private String phone;

    @NotNull
    @Pattern(regexp = "^.{8}$" , message = "Password must be less than 8")
    @NotBlank(message = "Password is required")
    private String password;
}
