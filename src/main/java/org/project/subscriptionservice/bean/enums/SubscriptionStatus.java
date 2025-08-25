package org.project.subscriptionservice.bean.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum SubscriptionStatus implements IEnum<String> {

    ACTIVE, CANCELLED, SUSPENDED, EXPIRED;

    private final String value;

    SubscriptionStatus() {
        this.value = this.name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
