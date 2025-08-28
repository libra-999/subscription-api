package org.project.subscriptionservice.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InvoiceDetailResponse {

    private String invoiceNo;
    private LocalDateTime dueDate;
    private String currency;
    private double total;
    private String status;
    private SubResponse subscription;

}
