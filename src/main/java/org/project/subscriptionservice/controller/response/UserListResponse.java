package org.project.subscriptionservice.controller.response;

import lombok.Data;

import java.util.Date;

@Data
public class UserListResponse {

    private Integer id;
    private String email;
    private String username;
    private String active;
    private String job;
    private String createdBy;
    private Date createdDate;
}
