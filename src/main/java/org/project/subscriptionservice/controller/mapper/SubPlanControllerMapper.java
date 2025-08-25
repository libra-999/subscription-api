package org.project.subscriptionservice.controller.mapper;

import org.mapstruct.*;
import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.controller.response.SubPlanDetailResponse;
import org.project.subscriptionservice.controller.response.SubPlanResponse;

@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SubPlanControllerMapper {

    @Mapping(target = "period", source = "billingCycle")
    SubPlanResponse fromList(SubscriptionPlanEntity entity);

    @Mapping(target = "period", source = "billingCycle")
    @Mapping(target = "createdDate", source = "createdAt")
    @Mapping(target = "updatedDate", source = "updatedAt")
    SubPlanDetailResponse fromDetail(SubscriptionPlanEntity entity);

}
