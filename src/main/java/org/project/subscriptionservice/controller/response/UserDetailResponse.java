package org.project.subscriptionservice.controller.response;

import lombok.Data;

@Data
public class UserDetailResponse {

    private String email;
    private String username;
    private String active;
    private String job;
    private String phone;
    private Integer locked;
    private double balance;
    private String createdBy;
    private String updatedBy;
}
