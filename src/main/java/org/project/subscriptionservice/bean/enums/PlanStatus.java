package org.project.subscriptionservice.bean.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum PlanStatus implements IEnum<String> {

    MONTHLY, YEAR, DAY, WEEKLY;

    private final String value;

    PlanStatus() {
        this.value = this.name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
