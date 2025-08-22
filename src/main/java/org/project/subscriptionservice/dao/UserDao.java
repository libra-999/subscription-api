package org.project.subscriptionservice.dao;

import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.share.entity.Paging;

public interface UserDao extends Dao<UserEntity> {

   Paging<UserEntity> list(String keyword, Integer page, Integer size, String username);

   UserEntity getByUsername(String username);

   UserEntity getByEmail(String email);

   UserEntity getByPhone(String phone);
}
