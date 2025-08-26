package org.project.subscriptionservice.domain.service;

import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.controller.request.SubCreation;
import org.project.subscriptionservice.share.entity.MetaData;
import org.project.subscriptionservice.share.entity.PaginationQuery;
import org.project.subscriptionservice.share.entity.Paging;

public interface SubService {

    Paging<SubscriptionEntity> list(PaginationQuery query, String status , String startDate ,String endDate , MetaData metaData);

    SubscriptionEntity view(Integer id, MetaData metaData);

    SubscriptionEntity create(SubCreation request, MetaData metaData);

    SubscriptionEntity cancel(Integer id, Integer userId , MetaData metaData);

    void invite(Integer id, String [] email, MetaData metaData);
}
