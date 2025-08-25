package org.project.subscriptionservice.bean;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;
import org.project.subscriptionservice.bean.enums.CurrencyStatus;
import org.project.subscriptionservice.bean.enums.PlanStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@TableName("SUBPLANS")
@KeySequence("SEQ_SUBPLANS")
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlanEntity {

    @TableId(type = IdType.AUTO, value = "id")
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("plan_ref")
    private String planRef;

    @TableField("description")
    private String description;

    @TableField("price")
    @Digits(fraction = 4, integer = 11)
    private BigDecimal price;

    @TableField("currency")
    private CurrencyStatus currency;

    @TableField("billing_cycle")
    private PlanStatus billingCycle;

    @TableField(exist = false)
    private List<SubscriptionEntity>  subscriptions;

    @TableField(value = "created_at", fill =  FieldFill.INSERT, jdbcType = JdbcType.TIMESTAMP)
    private Date createdAt;

    @TableField(value = "updated_at", fill =   FieldFill.INSERT_UPDATE, jdbcType = JdbcType.TIMESTAMP)
    private Date updatedAt;
}
