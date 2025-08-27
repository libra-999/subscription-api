package org.project.subscriptionservice.dao;

import org.project.subscriptionservice.bean.SubscriptionMemberEntity;

public interface SubMemberDao extends Dao<SubscriptionMemberEntity> {

    SubscriptionMemberEntity findSubAndUser(Integer subId, Integer userId);
}
