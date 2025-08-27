package org.project.subscriptionservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.bean.enums.SubscriptionStatus;

import java.time.LocalDateTime;
import java.util.Date;

public interface SubDaoMapper extends BaseMapper<SubscriptionEntity> {

    Page<SubscriptionEntity> listAll(Page<SubscriptionPlanEntity> page,
                                     String keyword,
                                     Date startDate,
                                     Date endDate,
                                     SubscriptionStatus status,
                                     @Param("username") String username);

    SubscriptionEntity findById(String username , Integer id);

    SubscriptionEntity findByPlanId(Integer id);

    boolean checkEmailSub(Integer id, Integer userId, LocalDateTime time);
}
