package org.project.subscriptionservice.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.bean.enums.SubscriptionStatus;
import org.project.subscriptionservice.dao.SubDao;
import org.project.subscriptionservice.dao.mapper.SubDaoMapper;
import org.project.subscriptionservice.share.entity.PageNumber;
import org.project.subscriptionservice.share.entity.Paging;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public class SubDaoImpl extends DaoImpl<SubscriptionEntity, SubDaoMapper> implements SubDao {

    @Override
    public Paging<SubscriptionEntity> list(String keyword, Date startDate, Date endDate, Integer page, Integer size, SubscriptionStatus status, String username) {

        Page<SubscriptionEntity> entityPage = baseMapper.listAll(
            new Page<>(PageNumber.in(page), size),
            keyword, startDate, endDate, status, username);

        return Paging.<SubscriptionEntity>builder()
            .items(entityPage.getRecords().stream().toList())
            .page(PageNumber.out((int) entityPage.getCurrent()))
            .size((int) entityPage.getSize())
            .total(entityPage.getTotal())
            .totalPages((int) entityPage.getPages()).build();
    }

    @Override
    public SubscriptionEntity view(Integer id, String username) {
        return baseMapper.findById(username, id);
    }

    @Override
    public boolean checkEmail(Integer id, Integer userId, LocalDateTime time) {
        return false;
    }
}
