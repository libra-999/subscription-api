package org.project.subscriptionservice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.dao.UserDao;
import org.project.subscriptionservice.dao.mapper.UserDaoMapper;
import org.project.subscriptionservice.share.entity.PageNumber;
import org.project.subscriptionservice.share.entity.Paging;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class UserDaoImpl extends DaoImpl<UserEntity, UserDaoMapper> implements UserDao {

    @Override
    public Paging<UserEntity> list(String keyword, Integer page, Integer size, String username) {

        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery(UserEntity.class);
        queryWrapper.isNull(UserEntity::getDeletedAt);
        queryWrapper.orderByDesc(UserEntity::getId);
        if (Objects.nonNull(keyword)) {
            queryWrapper
                .like(UserEntity::getUsername, keyword).or()
                .like(UserEntity::getEmail, keyword).or()
                .like(UserEntity::getPhone, keyword).or();
        }
        if (Objects.nonNull(username)) {
            queryWrapper.eq(UserEntity::getCreatedBy, username);
        }

        Page<UserEntity> userPage = baseMapper.selectPage(new Page<>(PageNumber.in(page), size), queryWrapper);
        return Paging.<UserEntity>builder()
            .items(userPage.getRecords().stream().toList())
            .page(PageNumber.out((int) userPage.getCurrent()))
            .size((int) userPage.getSize())
            .total(userPage.getTotal())
            .totalPages((int) userPage.getPages()).build();
    }

    @Override
    public UserEntity getByUsername(String username) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getUsername, username).isNull(UserEntity::getDeletedAt)
        );
    }

    @Override
    public UserEntity getByEmail(String email) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getEmail, email).isNull(UserEntity::getDeletedAt)
        );
    }

    @Override
    public UserEntity getByPhone(String phone) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(UserEntity.class).eq(UserEntity::getPhone, phone).isNull(UserEntity::getDeletedAt)
        );
    }

}
