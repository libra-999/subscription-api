package org.project.subscriptionservice.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class InviteUserRequest {

    private List<String> emails;

}
