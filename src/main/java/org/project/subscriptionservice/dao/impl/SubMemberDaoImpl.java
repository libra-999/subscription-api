package org.project.subscriptionservice.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.project.subscriptionservice.bean.SubscriptionMemberEntity;
import org.project.subscriptionservice.bean.enums.SubRoleConstant;
import org.project.subscriptionservice.dao.SubMemberDao;
import org.project.subscriptionservice.dao.mapper.SubMemberDaoMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SubMemberDaoImpl extends DaoImpl<SubscriptionMemberEntity, SubMemberDaoMapper> implements SubMemberDao {

    @Override
    public SubscriptionMemberEntity findSubAndUser(Integer subId, Integer userId) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(SubscriptionMemberEntity.class)
                .eq(SubscriptionMemberEntity::getUserId, userId)
                .eq(SubscriptionMemberEntity::getSubscriptionId, subId)
        );
    }

    @Override
    public SubscriptionMemberEntity findOwnerSub(Integer id) {
        return baseMapper.selectOne(
            Wrappers.lambdaQuery(SubscriptionMemberEntity.class)
                .eq(SubscriptionMemberEntity::getRoleConstant, SubRoleConstant.OWNER)
                .eq(SubscriptionMemberEntity::getSubscriptionId, id).last("limit 1")
        );
    }
}
