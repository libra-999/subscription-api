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

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("PAYMENTS")
@KeySequence("SEQ_PAYMENTS")
public class PaymentEntity extends BaseEntity {

    @TableField("invoice_id")
    private Integer invoiceId;

    @TableField("user_id")
    private Integer userId;

    @TableField("reference")
    private String reference;

    @TableField("amount")
    @Digits(fraction = 4, integer = 11)
    private BigDecimal amount;

    @TableField("currency")
    private CurrencyStatus currency;

    @TableField("payment_method")
    private PaymentMethodStatus paymentMethod;

    @TableField(value = "street")
    private String street;

    @TableField(value = "city")
    private String city;

    @TableField(value = "state")
    private String state;

    @TableField(value = "zip_code")
    private Integer zipCode;

    @TableField("country")
    private String country;

    @TableField("status")
    private PaymentStatus status;

    @TableField("payment_date")
    private LocalDateTime paymentDate;

    @TableField(exist = false)
    private InvoiceEntity invoice;

    @TableField(exist = false)
    private UserEntity user;
}
