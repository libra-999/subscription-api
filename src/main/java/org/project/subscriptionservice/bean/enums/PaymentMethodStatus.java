package org.project.subscriptionservice.bean.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum PaymentMethodStatus implements IEnum<String>{

    CASH, MASTER_CARD, PAYPAL, VISA, CRYPTO;

    private final String value;

    PaymentMethodStatus(){
        this.value = this.name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
