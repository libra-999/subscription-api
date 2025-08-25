package org.project.subscriptionservice.controller.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.controller.response.SubDetailResponse;
import org.project.subscriptionservice.controller.response.SubResponse;

@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SubControllerMapper {

    SubResponse fromList(SubscriptionEntity entity);

    SubDetailResponse fromDetail(SubscriptionEntity entity);

}
