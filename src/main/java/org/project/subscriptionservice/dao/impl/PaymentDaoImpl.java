package org.project.subscriptionservice.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.project.subscriptionservice.bean.PaymentEntity;
import org.project.subscriptionservice.dao.PaymentDao;
import org.project.subscriptionservice.dao.mapper.PaymentDaoMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDaoImpl extends DaoImpl<PaymentEntity, PaymentDaoMapper> implements PaymentDao {

    @Override
    public PaymentEntity view(String ref) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(PaymentEntity.class)
                .eq(PaymentEntity::getReference, ref)
                .isNull(PaymentEntity::getDeletedAt)
        );
    }
}
