package org.project.subscriptionservice.controller.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetailResponse {

    private String email;
    private String username;
    private String active;
    private String job;
    private int phone;
    private String locked;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
}
