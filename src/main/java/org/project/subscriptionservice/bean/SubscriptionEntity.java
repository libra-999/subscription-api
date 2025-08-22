package org.project.subscriptionservice.bean;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.subscriptionservice.bean.enums.SubscriptionStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("SUBSCRIPTIONS")
@KeySequence("SEQ_SUBSCRIPTION")
public class SubscriptionEntity extends BaseEntity {

    @TableField("user_id")
    private Integer userId;

    @TableField("plan_id")
    private Integer planId;

    @TableField("status")
    private SubscriptionStatus status;

    @TableField("plan_start")
    private LocalDateTime planStart;

    @TableField("plan_end")
    private LocalDateTime planEnd;
}
