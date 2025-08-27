package org.project.subscriptionservice.bean;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.subscriptionservice.bean.enums.AccountStatus;

import java.math.BigDecimal;

@Data
@TableName("USERS")
@KeySequence("SEQ_USERS")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "email")
    private String email;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "job")
    private String job;

    @TableField(value = "amount")
    private BigDecimal balance;

    @TableField(value = "locked")
    private Integer locked;

    @TableField(value = "active")
    private AccountStatus active;

    @TableField("invite_code")
    private String inviteCode;

    @TableField("invited_by")
    private Integer inviteBy;

}
