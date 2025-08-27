package org.project.subscriptionservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserSubResponse {

    private Integer id;
    private String email;
    private String username;
    private String active;
    private String job;
    private String role;
    private boolean subscribeStatus;
}
