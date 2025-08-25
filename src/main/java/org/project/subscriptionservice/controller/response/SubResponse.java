package org.project.subscriptionservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SubResponse {

    private Integer id;
    private List<UserListResponse> users;
    private SubPlanResponse plan;
    private LocalDateTime planStart;
    private LocalDateTime planEnd;
    private Date createdDate;
    private String createdBy;
}
