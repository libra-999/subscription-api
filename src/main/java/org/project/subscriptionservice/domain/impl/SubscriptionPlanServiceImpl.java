package org.project.subscriptionservice.domain.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.bean.enums.CurrencyStatus;
import org.project.subscriptionservice.bean.enums.PlanStatus;
import org.project.subscriptionservice.controller.request.SubPlanCreation;
import org.project.subscriptionservice.controller.request.SubPlanUpdated;
import org.project.subscriptionservice.dao.SubPlanDao;
import org.project.subscriptionservice.domain.exception.SubPlanException;
import org.project.subscriptionservice.domain.exception.UserException;
import org.project.subscriptionservice.domain.service.SubscriptionPlanService;
import org.project.subscriptionservice.share.entity.MetaData;
import org.project.subscriptionservice.share.entity.PaginationQuery;
import org.project.subscriptionservice.share.entity.Paging;
import org.project.subscriptionservice.share.globalException.HttpException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubPlanDao subPlanDao;

    @Override
    @Transactional(readOnly = true)
    public Paging<SubscriptionPlanEntity> list(PaginationQuery query, MetaData metaData) {
        return subPlanDao.list(query.getKeyword(), query.getPage(), query.getSize(), metaData.getUsername());
    }

    @Override
    public SubscriptionPlanEntity view(Integer id, MetaData metaData) {
        try {
            return subPlanDao.findSubPlan(id);
        } catch (HttpException e) {
            throw UserException.notFound();
        }
    }

    @Override
    @Transactional(rollbackFor = HttpException.class)
    public SubscriptionPlanEntity create(SubPlanCreation request, MetaData metaData) {
        SubscriptionPlanEntity entity = new SubscriptionPlanEntity();
        String uuid = UUID.randomUUID().toString().substring(0, 10);
        String ref = "plan_" + uuid;

        if (subPlanDao.getAlreadyName(request.getName()) != null) {
            throw SubPlanException.alreadyExist();
        }

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPrice(BigDecimal.valueOf(request.getPrice()));
        entity.setCurrency(CurrencyStatus.valueOf(request.getCurrency()));
        entity.setMaxParticipate(request.getParticipate());
        entity.setBillingCycle(PlanStatus.valueOf(request.getPeriod()));
        entity.setPlanRef(ref);
        entity.setCreatedAt(new Date());
        log.info("... Plan service:  {} ...", entity.getName());

        subPlanDao.create(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = HttpException.class)
    public SubscriptionPlanEntity update(Integer id, SubPlanUpdated request, MetaData metaData) {

        SubscriptionPlanEntity entity = subPlanDao.view(id);
        if (subPlanDao.getAlreadyName(request.getName()) != null) {
            throw SubPlanException.alreadyExist();
        }

        if (request.getPrice() >= 0) {
            entity.setPrice(BigDecimal.valueOf(request.getPrice()));
        } else entity.setPrice(entity.getPrice());

        if (request.getPeriod() != null) {
            entity.setBillingCycle(PlanStatus.valueOf(request.getPeriod()));
        } else entity.setBillingCycle(entity.getBillingCycle());

        if (request.getCurrency() != null) {
            entity.setCurrency(CurrencyStatus.valueOf(request.getCurrency()));
        } else entity.setCurrency(entity.getCurrency());

        entity.setName(Objects.nonNull(request.getName()) ? request.getName() : entity.getName());
        entity.setDescription(Objects.nonNull(request.getDescription()) ? request.getDescription() : entity.getDescription());
        entity.setPrice(BigDecimal.valueOf(request.getPrice()));
        entity.setUpdatedAt(new Date());

        subPlanDao.update(entity, id);
        return entity;
    }
}
