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

    @Mapping(target = "createdDate", source = "createdAt")
    UserListResponse toList(UserEntity users);

    @Mapping(target = "createdDate", source = "createdAt")
    @Mapping(target = "updatedDate", source = "updatedAt")
    @Mapping(target = "phone", source = "phone")
    UserDetailResponse toDetail(UserEntity users);

}
