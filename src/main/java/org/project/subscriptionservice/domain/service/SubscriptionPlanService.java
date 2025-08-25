package org.project.subscriptionservice.domain.service;

import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.controller.request.SubPlanCreation;
import org.project.subscriptionservice.controller.request.SubPlanUpdated;
import org.project.subscriptionservice.share.entity.MetaData;
import org.project.subscriptionservice.share.entity.PaginationQuery;
import org.project.subscriptionservice.share.entity.Paging;

public interface SubscriptionPlanService {

    Paging<SubscriptionPlanEntity> list(PaginationQuery query, MetaData metaData);

    SubscriptionPlanEntity view(Integer id, MetaData metaData);

    SubscriptionPlanEntity create(SubPlanCreation request, MetaData metaData);

    SubscriptionPlanEntity update(Integer id, SubPlanUpdated request, MetaData metaData);


}
