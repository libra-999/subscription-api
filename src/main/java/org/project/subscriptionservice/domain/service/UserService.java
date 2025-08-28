package org.project.subscriptionservice.domain.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.controller.request.DepositRequest;
import org.project.subscriptionservice.controller.request.Login;
import org.project.subscriptionservice.controller.request.Register;
import org.project.subscriptionservice.controller.request.UserCreation;
import org.project.subscriptionservice.share.entity.MetaData;
import org.project.subscriptionservice.share.entity.PaginationQuery;
import org.project.subscriptionservice.share.entity.Paging;

import java.util.Map;

public interface UserService {

    Paging<UserEntity> list(PaginationQuery query, MetaData metaData);

    UserEntity view(Integer id, MetaData metaData);

    UserEntity create(UserCreation request, MetaData metaData);

    Map<String, String> register(Register entity);

    Map<String,String> login(Login entity);

    Map<String , String> code(HttpServletRequest res);

    void imageCode(String uuid, HttpServletResponse response);

    void delete(Integer id, MetaData metaData);

    UserEntity deposit(Integer id, DepositRequest request, MetaData metaData);

}
