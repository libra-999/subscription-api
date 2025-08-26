package org.project.subscriptionservice.dao.impl;

import org.project.subscriptionservice.bean.SubscriptionMemberEntity;
import org.project.subscriptionservice.dao.SubMemberDao;
import org.project.subscriptionservice.dao.mapper.SubMemberDaoMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SubMemberDaoImpl extends DaoImpl<SubscriptionMemberEntity, SubMemberDaoMapper> implements SubMemberDao {

}
