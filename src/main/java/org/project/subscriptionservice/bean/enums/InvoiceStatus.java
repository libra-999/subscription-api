package org.project.subscriptionservice.bean.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum InvoiceStatus implements IEnum<String> {

    PAID, CANCELLED, PENDING, OVERDUE;

    private final String value;

    InvoiceStatus() {
        this.value = this.name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
