package org.project.subscriptionservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SubPlanDetailResponse {

    private String name;
    private String currency;
    private BigDecimal price;
    private String period;
    private String description;
    private Date createdDate;
    private Date updatedDate;

}
