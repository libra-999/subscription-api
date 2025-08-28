package org.project.subscriptionservice.domain.service;

import org.project.subscriptionservice.bean.InvoiceEntity;
import org.project.subscriptionservice.bean.SubscriptionPlanEntity;

public interface InvoiceService {

    InvoiceEntity create(Integer userId, Integer subId, SubscriptionPlanEntity subPlan);

    InvoiceEntity view(Integer id);

    InvoiceEntity generateInvoice();
}
