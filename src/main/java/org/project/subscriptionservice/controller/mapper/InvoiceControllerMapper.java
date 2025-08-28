package org.project.subscriptionservice.controller.mapper;

import org.mapstruct.*;
import org.project.subscriptionservice.bean.InvoiceEntity;
import org.project.subscriptionservice.controller.response.InvoiceDetailResponse;

@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface InvoiceControllerMapper {

    @Mapping(target = "total", source = "amount")
    @Mapping(target = "subscription", source = "subscription")
    @Mapping(target = "subscription.plan", source = "subscription.subscriptionPlan")
    @Mapping(target = "subscription.plan.period", source = "subscription.subscriptionPlan.billingCycle")
    InvoiceDetailResponse from(InvoiceEntity entity);

}
