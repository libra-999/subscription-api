package org.project.subscriptionservice.controller.mapper;

import org.mapstruct.*;
import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.bean.SubscriptionMemberEntity;
import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.controller.response.SubPlanDetailResponse;
import org.project.subscriptionservice.controller.response.SubPlanResponse;
import org.project.subscriptionservice.controller.response.UserListResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SubPlanControllerMapper {

    @Mapping(target = "period", source = "billingCycle")
    SubPlanResponse fromList(SubscriptionPlanEntity entity);

    @Mapping(target = "period", source = "billingCycle")
    @Mapping(target = "users", expression = "java(listUsers(entity.getSubscriptions()))")
    SubPlanDetailResponse fromDetail(SubscriptionPlanEntity entity);

    @Mapping(target = "username", source = "username")
    UserListResponse to(UserEntity user);

    default List<UserListResponse> listUsers(List<SubscriptionEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream()
            .filter(Objects::nonNull)
            .flatMap(sub -> sub.getMembers().stream())
            .filter(Objects::nonNull)
            .map(SubscriptionMemberEntity::getUser)
            .filter(Objects::nonNull)
            .map(this::to)
            .toList();
    }
}
