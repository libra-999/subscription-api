package org.project.subscriptionservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SubPlanDetailResponse {

    private String planRef;
    private String name;
    private String currency;
    private BigDecimal price;
    private String period;
    private List<UserListResponse> users;
    private String description;
}
