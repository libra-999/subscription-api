package org.project.subscriptionservice.bean;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.subscriptionservice.bean.constant.AccountStatus;

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

    @TableField(value = "locked")
    private Boolean locked;

    @TableField(value = "active")
    private AccountStatus active;

}
