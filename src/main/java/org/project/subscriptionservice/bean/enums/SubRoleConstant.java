package org.project.subscriptionservice.bean.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum SubRoleConstant implements IEnum<String> {
    MEMBER , OWNER;

    private final String value;

    SubRoleConstant() {
        this.value = this.name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
