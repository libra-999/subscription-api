package org.project.subscriptionservice.bean.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum CurrencyStatus implements IEnum<String> {

    USD, KHR, GBP, JPY, INR, CNY;
    private final String value;

    CurrencyStatus() {
        this.value = this.name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
