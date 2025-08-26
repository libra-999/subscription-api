package org.project.subscriptionservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubDetailResponse {

    private SubPlanResponse plan;
    private UserDetailResponse user;
    private Boolean autoRenew;
    private Boolean isTrail;
    private String status;
    private String planStart;
    private String planEnd;
    private String createdBy;
    private String updatedBy;
}
