package org.project.subscriptionservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubDetailResponse {

    private SubPlanResponse plan;
    private String status;
    private LocalDateTime planStart;
    private LocalDateTime planEnd;
    private List<UserListResponse> users;
    private Date createdDate;
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;
}
