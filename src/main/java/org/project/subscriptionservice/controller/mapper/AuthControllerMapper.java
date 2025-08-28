package org.project.subscriptionservice.controller.mapper;

import org.mapstruct.*;
import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.controller.response.AuthResponse;
import org.project.subscriptionservice.controller.response.UserDetailResponse;
import org.project.subscriptionservice.controller.response.UserListResponse;

@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AuthControllerMapper {

    AuthResponse to(UserEntity user);

    @Mapping(target = "balance", source = "balance")
    UserListResponse toList(UserEntity users);

    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "updatedBy", source = "updatedBy")
    @Mapping(target = "locked", source = "locked")
    @Mapping(target = "balance", source = "balance")
    UserDetailResponse toDetail(UserEntity users);

}
