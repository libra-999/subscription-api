package org.project.subscriptionservice.domain.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.project.subscriptionservice.aop.MetaHandler;
import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.bean.enums.SubscriptionStatus;
import org.project.subscriptionservice.controller.request.SubCreation;
import org.project.subscriptionservice.dao.SubDao;
import org.project.subscriptionservice.domain.exception.SubException;
import org.project.subscriptionservice.domain.service.SubService;
import org.project.subscriptionservice.share.entity.MetaData;
import org.project.subscriptionservice.share.entity.PaginationQuery;
import org.project.subscriptionservice.share.entity.Paging;
import org.project.subscriptionservice.share.globalException.HttpException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SubServiceImpl implements SubService {

    private final SubDao subDao;

    @Override
    @MetaHandler
    @SneakyThrows
    @Transactional(readOnly = true)
    public Paging<SubscriptionEntity> list(PaginationQuery query, String status, String startDate, String endDate, MetaData metaData) {
        SubscriptionStatus subscriptionStatus = null;

        if (startDate != null || endDate != null || status != null) {
            Date startDateDate = convertStringToDate(startDate);
            Date endDateDate = convertStringToDate(endDate);
            subscriptionStatus = SubscriptionStatus.valueOf(status);
            return subDao.list(query.getKeyword(), startDateDate, endDateDate,
                query.getPage(), query.getSize(), subscriptionStatus, metaData.getUsername());
        }
        return subDao.list(query.getKeyword(), null, null,
            query.getPage(), query.getSize(), subscriptionStatus, metaData.getUsername());
    }

    @Override
    @MetaHandler
    public SubscriptionEntity view(Integer id, MetaData metaData) {
        try {
            return subDao.view(id, metaData.getUsername());
        } catch (HttpException e) {
            throw SubException.notFound();
        }
    }

    @Override
    public SubscriptionEntity create(SubCreation request, MetaData metaData) {
        return null;
    }

    @SneakyThrows
    private static Date convertStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

}
