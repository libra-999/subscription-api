package org.project.subscriptionservice.domain.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.project.subscriptionservice.aop.MetaHandler;
import org.project.subscriptionservice.aop.PaymentHandler;
import org.project.subscriptionservice.bean.*;
import org.project.subscriptionservice.bean.enums.*;
import org.project.subscriptionservice.context.PushEmailService;
import org.project.subscriptionservice.controller.request.InviteUserRequest;
import org.project.subscriptionservice.controller.request.SubCreation;
import org.project.subscriptionservice.dao.*;
import org.project.subscriptionservice.domain.exception.SubException;
import org.project.subscriptionservice.domain.exception.SubPlanException;
import org.project.subscriptionservice.domain.exception.UserException;
import org.project.subscriptionservice.domain.service.InvoiceService;
import org.project.subscriptionservice.domain.service.SubService;
import org.project.subscriptionservice.share.entity.MetaData;
import org.project.subscriptionservice.share.entity.PaginationQuery;
import org.project.subscriptionservice.share.entity.Paging;
import org.project.subscriptionservice.share.globalException.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubServiceImpl implements SubService {

    private final SubDao subDao;
    private final SubPlanDao subPlanDao;
    private final UserDao userDao;
    private final InvoiceDao invoiceDao;
    private final PaymentDao paymentDao;
    private final SubMemberDao subMemberDao;
    private final PushEmailService pushEmailService;
    private final InvoiceService invoiceService;

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
    @Transactional(readOnly = true)
    public SubscriptionEntity view(Integer id, MetaData metaData) {
        SubscriptionEntity sub = subDao.view(id, metaData.getUsername());
        if (sub == null) {
            throw SubException.notFound();
        }
        return sub;
    }

    @Override
    @Deprecated
    @MetaHandler
    @PaymentHandler
    @Transactional(rollbackFor = HttpException.class)
    public SubscriptionEntity create(SubCreation request, MetaData metaData) {
        UserEntity userEntity = userDao.getByUsername(request.getUser().getUsername());
        SubscriptionPlanEntity subPlanEntity = subPlanDao.getPlanRef(request.getPlan().getRef());

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        if (userEntity == null) {
            throw UserException.notFound();
        } else if (!Objects.equals(userEntity.getEmail(), request.getUser().getEmail())) {
            throw UserException.invalidEmail();
        } else if (!Objects.equals(userEntity.getPhone(), request.getUser().getPhone())) {
            throw UserException.invalidPhone();
        }

        if (subPlanEntity == null) {
            throw SubPlanException.notFound();
        } else if (!Objects.equals(subPlanEntity.getId(), request.getPlan().getPlan_id())) {
            throw SubPlanException.notFound();
        }

        try {

            subscriptionEntity.setPlanId(subPlanEntity.getId());
            subscriptionEntity.setSubscriptionPlan(subPlanEntity);
            CycleBilling(subscriptionEntity, subPlanEntity);

            subscriptionEntity.setIsTrial(true);
            subscriptionEntity.setAutoRenew(request.getAutoRenew());
            subscriptionEntity.setStatus(SubscriptionStatus.ACTIVE);
            subscriptionEntity.setCreatedAt(new Date());
            subscriptionEntity.setCreatedBy(metaData.getUsername());

            subDao.create(subscriptionEntity); // save subscribe then get object

            SubscriptionEntity save = subDao.view(subscriptionEntity.getId());
            InvoiceEntity invoiceEntity = new InvoiceEntity();
            invoiceEntity.setSubscriptionId(save.getId());
            invoiceEntity.setInvoiceNo(generateInvoiceNumber(save.getId()));
            invoiceEntity.setAmount(subPlanEntity.getPrice());
            invoiceEntity.setCurrency(subPlanEntity.getCurrency());
            invoiceEntity.setDueDate(save.getPlanEnd());
            invoiceEntity.setStatus(InvoiceStatus.PENDING);
            invoiceEntity.setSubscription(subscriptionEntity);
            invoiceEntity.setCreatedAt(new Date());
            invoiceEntity.setCreatedBy(metaData.getUsername());
            invoiceDao.create(invoiceEntity);

            SubscriptionMemberEntity memberEntity = new SubscriptionMemberEntity();
            memberEntity.setSubscriptionId(save.getId());
            memberEntity.setUserId(userEntity.getId());
            memberEntity.setRoleConstant(SubRoleConstant.OWNER);
            memberEntity.setAccepted(Boolean.TRUE);
            memberEntity.setInvitedAt(LocalDateTime.now());
            subMemberDao.create(memberEntity);

            PaymentEntity paymentEntity = new PaymentEntity();
            InvoiceEntity invoiceSave = invoiceDao.view(invoiceEntity.getId());

            paymentEntity.setInvoiceId(invoiceSave.getId());
            paymentEntity.setUserId(userEntity.getId());
            paymentEntity.setInvoice(invoiceSave);
            paymentEntity.setReference(generateRefer(invoiceEntity.getId()));
            paymentEntity.setAmount(subPlanEntity.getPrice());
            paymentEntity.setCurrency(subPlanEntity.getCurrency());
            paymentEntity.setPaymentMethod(PaymentMethodStatus.valueOf(request.getPayment().getPaymentMethod()));
            paymentEntity.setCity(request.getPayment().getBillingAddress().getCity());
            paymentEntity.setState(request.getPayment().getBillingAddress().getState());
            paymentEntity.setStreet(request.getPayment().getBillingAddress().getStreet());
            paymentEntity.setZipCode(request.getPayment().getBillingAddress().getZipCode());
            paymentEntity.setCountry(request.getPayment().getBillingAddress().getCountry());
            paymentEntity.setStatus(PaymentStatus.PENDING);
            paymentEntity.setPaymentDate(LocalDateTime.now());
            paymentEntity.setCreatedAt(new Date());
            paymentEntity.setCreatedBy(metaData.getUsername());
            paymentDao.create(paymentEntity);
        } catch (Exception e) {
            throw new SubException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return subscriptionEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows
    public SubscriptionEntity cancel(Integer id, Integer userId, MetaData metaData) {
        SubscriptionMemberEntity planMember = subMemberDao.findSubAndUser(id, userId);
        if (planMember == null) {
            throw SubPlanException.notFound();
        }

        UserEntity user = userDao.view(userId);
        SubscriptionEntity subscribe = subDao.view(id);

        try {
            if (user.getActive() == AccountStatus.ACTIVE
                && user.getLocked() == 0
                && subscribe.getStatus() == SubscriptionStatus.ACTIVE) {

                subscribe.setStatus(SubscriptionStatus.CANCELLED);
                subscribe.setAutoRenew(false);
                subDao.update(subscribe, id);

                planMember.setAccepted(false);
                subMemberDao.update(planMember, planMember.getId());
                pushEmailService.sendMessage(user.getEmail(), "没钱 :)", "Subscription");
            }
        } catch (Exception e) {
            throw new SubException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return subscribe;
    }

    @Override
    @MetaHandler
    @Transactional(rollbackFor = HttpException.class)
    public void invite(String id, InviteUserRequest request, MetaData metaData) {
        SubscriptionPlanEntity subPlan = subPlanDao.findRef(id);
        if (subPlan == null) throw SubPlanException.notFound();

        SubscriptionEntity subscription = subDao.findPlan(subPlan.getId());
        if (subscription == null) throw SubPlanException.notFound();

        Integer subId = subscription.getId();
        LocalDateTime now = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString().substring(1, 5);

        if (subPlan.getMaxParticipate() <= request.getEmails().size()) {
            throw SubException.maxJoin();
        }

        for (String email : request.getEmails()) {
            UserEntity user = userDao.getByEmail(email);
            if (user == null) {
                throw UserException.notFound();
            } else {
                boolean existEmailSub = subDao.checkEmail(subId, user.getId(), now);
                if (existEmailSub) {
                    throw SubException.existEmailSub();
                }
                SubscriptionMemberEntity member = new SubscriptionMemberEntity();
                member.setUserId(user.getId());
                member.setInvitedAt(LocalDateTime.now());
                member.setRoleConstant(SubRoleConstant.MEMBER);
                member.setSubscription(subscription);
                member.setAccepted(true);
                member.setSubscriptionId(subId);
                subMemberDao.create(member);

                UserEntity userInvite = userDao.getByUsername(metaData.getUsername());
                user.setInviteBy(userInvite.getId());
                user.setInviteCode(userInvite.getUsername() + "_" + uuid);
                userDao.update(user, user.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = HttpException.class)
    public void checkRenew() {
        List<SubscriptionEntity> subscriptionEntityList = subDao.listAllUserWithExpired(LocalDateTime.now());
        for (SubscriptionEntity entity : subscriptionEntityList) {
            if (entity.getAutoRenew()){

                SubscriptionPlanEntity subPlan = subPlanDao.findSubPlan(entity.getPlanId());
                if (subPlan == null) throw SubPlanException.notFound();

                BigDecimal price = subPlan.getPrice();
                SubscriptionMemberEntity memberEntity = subMemberDao.findOwnerSub(entity.getId());

                Integer ownerId = memberEntity.getUserId();
                UserEntity owner = userDao.view(ownerId, memberEntity.getUserEntity().getUsername());
                if(owner == null) throw UserException.notFound();

                BigDecimal balanceOwner =  owner.getBalance();

                if (balanceOwner.compareTo(price) >= 0){
                    owner.setBalance(balanceOwner.subtract(price));
                    userDao.update(owner, owner.getId());

                    entity.setStatus(SubscriptionStatus.ACTIVE);
                    CycleBilling(entity, subPlan);

                    subDao.update(entity, entity.getId());
                    invoiceService.create(ownerId, entity.getId(), subPlan);
                }else {
                    throw UserException.outBalance();
                }
            }
        }
    }

    private void CycleBilling(SubscriptionEntity entity, SubscriptionPlanEntity subPlan) {
        switch (subPlan.getBillingCycle()) {
            case YEAR: {
                entity.setPlanStart(LocalDateTime.now());
                entity.setPlanEnd(LocalDateTime.now().plusYears(1));
                break;
            }
            case MONTHLY: {
                entity.setPlanStart(LocalDateTime.now());
                entity.setPlanEnd(LocalDateTime.now().plusMonths(1));
                break;
            }
            case WEEKLY: {
                entity.setPlanStart(LocalDateTime.now());
                entity.setPlanEnd(LocalDateTime.now().plusWeeks(1));
                break;
            }
            case DAY: {
                entity.setPlanStart(LocalDateTime.now());
                entity.setPlanEnd(LocalDateTime.now().plusDays(1));
                break;
            }
            default:
                break;
        }
    }

    @SneakyThrows
    private static Date convertStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    @Deprecated
    public static String generateInvoiceNumber(Integer id) {
        String uuid = UUID.randomUUID().toString().substring(1, 4);
        return "IN" + uuid + "-" + id;
    }

    public static String generateRefer(Integer id) {
        String uuid = UUID.randomUUID().toString().substring(1, 8);
        return String.format("SUB%s%08d", uuid, id);
    }

}
