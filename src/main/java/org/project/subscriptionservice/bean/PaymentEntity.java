package org.project.subscriptionservice.bean;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.subscriptionservice.bean.enums.CurrencyStatus;
import org.project.subscriptionservice.bean.enums.PaymentMethodStatus;
import org.project.subscriptionservice.bean.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("PAYMENTS")
@KeySequence("SEQ_PAYMENTS")
public class PaymentEntity extends BaseEntity {

    @TableField("invoice_id")
    private Integer invoiceId;

    @TableField("reference")
    private String reference;

    @TableField("amount")
    @Digits(fraction = 4, integer = 11)
    private BigDecimal amount;

    @TableField("currency")
    private CurrencyStatus currency;

    @TableField("payment_method")
    private PaymentMethodStatus paymentMethod;

    private AddressEntity address;

    @TableField("status")
    private PaymentStatus status;

    @TableField("payment_date")
    private LocalDateTime paymentDate;

    @TableField(exist = false)
    private InvoiceEntity invoice;


}
