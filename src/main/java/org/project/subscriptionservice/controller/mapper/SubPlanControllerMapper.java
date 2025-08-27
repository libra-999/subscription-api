package org.project.subscriptionservice.controller.mapper;

import org.mapstruct.*;
import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.bean.SubscriptionMemberEntity;
import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.controller.response.SubPlanDetailResponse;
import org.project.subscriptionservice.controller.response.SubPlanResponse;
import org.project.subscriptionservice.controller.response.UserSubResponse;

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
    @Mapping(target = "maxSubscriptions", source = "maxParticipate")
    @Mapping(target = "users", expression = "java(listUsers(entity.getSubscriptions()))")
    SubPlanDetailResponse fromDetail(SubscriptionPlanEntity entity);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "job", source = "user.job")
    @Mapping(target = "active", source = "user.active")
    @Mapping(target = "role", source = "member.roleConstant")
    @Mapping(target = "subscribeStatus", source = "member.accepted")
    UserSubResponse to(UserEntity user, SubscriptionMemberEntity member);

    default List<UserSubResponse> listUsers(List<SubscriptionEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream()
            .filter(Objects::nonNull)
            .flatMap(sub -> sub.getMembers().stream()
                .map(member -> this.to(member.getUserEntity(), member)))
            .toList();
    }
}
