package org.project.subscriptionservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.bean.enums.SubscriptionStatus;

import java.util.Date;

public interface SubDaoMapper extends BaseMapper<SubscriptionEntity> {

    Page<SubscriptionEntity> listAll(Page<SubscriptionPlanEntity> page,
                                     String keyword,
                                     Date startDate,
                                     Date endDate,
                                     SubscriptionStatus status,
                                     String username);

    SubscriptionEntity findById(String username , Integer id);
}
