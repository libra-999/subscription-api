package org.project.subscriptionservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SubPlanResponse {

    private Integer id;
    private String planRef;
    private String name;
    private String currency;
    private BigDecimal price;
    private String period;
}
