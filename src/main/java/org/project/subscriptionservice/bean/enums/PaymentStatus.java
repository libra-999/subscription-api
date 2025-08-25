package org.project.subscriptionservice.bean.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum PaymentStatus implements IEnum<String> {

    SUCCESS, FAILED, PENDING;

    private final String value;

    PaymentStatus() {
        this.value = this.name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
