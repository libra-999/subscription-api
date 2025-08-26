package org.project.subscriptionservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SubResponse {

    private Integer id;
    private UserListResponse user;
    private SubPlanResponse plan;
    private String planStart;
    private String planEnd;
    private String createdBy;
}
