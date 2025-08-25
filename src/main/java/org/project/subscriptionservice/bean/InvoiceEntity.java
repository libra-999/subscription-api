package org.project.subscriptionservice.bean;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.subscriptionservice.bean.enums.CurrencyStatus;
import org.project.subscriptionservice.bean.enums.InvoiceStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("INVOICES")
@KeySequence("SEQ_INVOICES")
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceEntity extends BaseEntity {

    @TableField("subscription_id")
    private Integer subscriptionId;

    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("amount")
    @Digits(fraction = 4, integer = 11)
    private BigDecimal amount;

    @TableField("currency")
    private CurrencyStatus currency;

    @TableField("due_date")
    private LocalDateTime dueDate;

    @TableField("status")
    private InvoiceStatus status;

    @TableField(exist = false)
    private SubscriptionEntity subscription;

    @TableField(exist = false)
    private List<PaymentEntity> payments;
}
