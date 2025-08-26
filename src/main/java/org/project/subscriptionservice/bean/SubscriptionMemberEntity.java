package org.project.subscriptionservice.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.subscriptionservice.bean.enums.SubRoleConstant;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("SUBSCRIPTION_MEMBER")
@KeySequence("SEQ_SUBSCRIPTION_MEMBER")
public class SubscriptionMemberEntity {

    @TableId(type = IdType.AUTO, value = "id")
    private Integer id;

    @TableField("subscription_id")
    private Integer subscriptionId;

    @TableField("user_id")
    private Integer userId;

    @TableField("role_status")
    private SubRoleConstant roleConstant;

    @TableField("accepted")
    private boolean accepted;

    @TableField("invited_at")
    private LocalDateTime invitedAt;

    @TableField(exist = false)
    private SubscriptionEntity subscription;

    @TableField(exist = false)
    private UserEntity user;
}
