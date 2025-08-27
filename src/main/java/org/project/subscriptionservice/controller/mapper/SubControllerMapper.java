package org.project.subscriptionservice.controller.mapper;


import org.mapstruct.*;
import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.controller.response.*;

@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SubControllerMapper {

    @Mapping(target = "planStart", source = "planStart")
    @Mapping(target = "planEnd", source = "planEnd")
    @Mapping(target = "plan", source = "subscriptionPlan")
    SubResponse fromList(SubscriptionEntity entity);

    @Mapping(target = "plan", source = "subscriptionPlan")
    SubDetailResponse fromDetail(SubscriptionEntity entity);

    @Mapping(target = "period", source = "billingCycle")
    SubPlanResponse from(SubscriptionPlanEntity entity);


}
