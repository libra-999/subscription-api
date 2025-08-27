package org.project.subscriptionservice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.dao.SubPlanDao;
import org.project.subscriptionservice.dao.mapper.SubPlanDaoMapper;
import org.project.subscriptionservice.share.entity.PageNumber;
import org.project.subscriptionservice.share.entity.Paging;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class SubPlanDaoImpl extends DaoImpl<SubscriptionPlanEntity, SubPlanDaoMapper> implements SubPlanDao {

    @Override
    public Paging<SubscriptionPlanEntity> list(String keyword, Integer page, Integer size, String username) {
        LambdaQueryWrapper<SubscriptionPlanEntity> queryWrapper = Wrappers.lambdaQuery(SubscriptionPlanEntity.class);
        if (Objects.nonNull(keyword)) {
            queryWrapper.like(SubscriptionPlanEntity::getName, keyword).or()
                .like(SubscriptionPlanEntity::getDescription, keyword).or()
                .like(SubscriptionPlanEntity::getBillingCycle, keyword);
        }

        Page<SubscriptionPlanEntity> pageResult = baseMapper.selectPage(new Page<>(PageNumber.in(page), size), queryWrapper);
        return Paging.<SubscriptionPlanEntity>builder()
            .items(pageResult.getRecords().stream().toList())
            .page(PageNumber.out((int) pageResult.getCurrent()))
            .size((int) pageResult.getSize())
            .total(pageResult.getTotal())
            .totalPages((int) pageResult.getPages()).build();
    }

    @Override
    public SubscriptionPlanEntity getAlreadyName(String name) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(SubscriptionPlanEntity.class)
                .eq(SubscriptionPlanEntity::getName, name)
        );
    }

    @Override
    public SubscriptionPlanEntity getPlanRef(String ref) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(SubscriptionPlanEntity.class)
                .eq(SubscriptionPlanEntity::getPlanRef, ref)
        );
    }

    @Override
    public SubscriptionPlanEntity findSubPlan(Integer id) {
        return baseMapper.findSubPlan(id);
    }

    @Override
    public SubscriptionPlanEntity findRef(String ref) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(SubscriptionPlanEntity.class).eq(SubscriptionPlanEntity::getPlanRef, ref)
        );
    }
}
