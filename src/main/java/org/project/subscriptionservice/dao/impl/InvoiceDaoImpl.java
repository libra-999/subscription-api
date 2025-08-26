package org.project.subscriptionservice.dao.impl;

import org.project.subscriptionservice.bean.InvoiceEntity;
import org.project.subscriptionservice.dao.InvoiceDao;
import org.project.subscriptionservice.dao.mapper.InvoiceDaoMapper;
import org.springframework.stereotype.Repository;

@Repository
public class InvoiceDaoImpl extends DaoImpl<InvoiceEntity, InvoiceDaoMapper> implements InvoiceDao {

}
