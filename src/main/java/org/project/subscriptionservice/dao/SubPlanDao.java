package org.project.subscriptionservice.dao;

import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.share.entity.Paging;

public interface SubPlanDao extends Dao<SubscriptionPlanEntity> {

    Paging<SubscriptionPlanEntity> list(String keyword, Integer page, Integer size, String username);

    SubscriptionPlanEntity getAlreadyName(String name);
}
