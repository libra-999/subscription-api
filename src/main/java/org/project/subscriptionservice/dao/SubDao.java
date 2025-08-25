package org.project.subscriptionservice.dao;

import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.bean.enums.SubscriptionStatus;
import org.project.subscriptionservice.share.entity.Paging;

import java.util.Date;

public interface SubDao extends Dao<SubscriptionEntity> {

    Paging<SubscriptionEntity> list(String keyword,
                                    Date startDate,
                                    Date endDate,
                                    Integer page,
                                    Integer size,
                                    SubscriptionStatus status,
                                    String username);

    SubscriptionEntity view(Integer id, String username);
}
