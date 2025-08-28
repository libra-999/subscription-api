package org.project.subscriptionservice.controller.mapper;

import org.mapstruct.*;
import org.project.subscriptionservice.bean.PaymentEntity;
import org.project.subscriptionservice.controller.response.PaymentResponse;

@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PaymentControllerMapper {


    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "address.street", source = "street")
    @Mapping(target = "address.city", source = "city")
    @Mapping(target = "address.state", source = "state")
    @Mapping(target = "address.zip", source = "zipCode")
    @Mapping(target = "address.country", source = "country")
    @Mapping(target = "price", source = "amount")
    PaymentResponse from(PaymentEntity entity);

}
