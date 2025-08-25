package org.project.subscriptionservice.bean.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum AccountStatus implements IEnum<String> {

    ACTIVE, SUSPENDED, DELETED;

    private final String value;

    AccountStatus() {
        this.value = this.name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
