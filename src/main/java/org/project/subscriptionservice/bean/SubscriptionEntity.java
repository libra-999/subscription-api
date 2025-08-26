package org.project.subscriptionservice.bean;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.subscriptionservice.bean.enums.SubscriptionStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("SUBSCRIPTION")
@KeySequence("SEQ_SUBSCRIPTION")
public class SubscriptionEntity extends BaseEntity {

    @TableField("plan_id")
    private Integer planId;

    @TableField("status")
    private SubscriptionStatus status;

    @TableField("plan_start")
    private LocalDateTime planStart;

    @TableField("plan_end")
    private LocalDateTime planEnd;

    @TableField("auto_renew")
    private Boolean autoRenew;

    @TableField("is_trial")
    private Boolean isTrial;

    @TableField(exist = false)
    private List<SubscriptionMemberEntity> members;

    @TableField(exist = false)
    private SubscriptionPlanEntity subscriptionPlan;

    @TableField(exist = false)
    private List<InvoiceEntity> invoices;

}
