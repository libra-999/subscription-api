package org.project.subscriptionservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
@Builder
public class AuthResponse {

    private String email;
    private String username;
    private String phone;
    private String job;
    private String active;
    private String token;
}
