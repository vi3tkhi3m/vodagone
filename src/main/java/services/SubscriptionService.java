package services;

import datasource.*;
import domain.Subscription;
import domain.UserSubscriptions;
import rest.dto.SubscriptionAllResponse;
import rest.dto.SubscriptionDetailsResponse;
import rest.dto.SubscriptionResponse;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionService {

    @Inject
    UserSubscriptionsDao userSubscriptionsDao;

    @Inject
    SubscriptionDao subscriptionDao;

    @Inject
    LoginDao loginDao;

    @PostConstruct
    public void init() {
        subscriptionDao.getSubscriptionList();
        System.out.println("Subscription List size: " + subscriptionDao.getSubscriptionList().size());
        userSubscriptionsDao.getAllUserSubscriptions();
        System.out.println("UserSubscription List size: " + userSubscriptionsDao.getAllUserSubscriptions().size());
        System.out.println("SubscriptionService is geladen!");
    }

    public SubscriptionResponse getAllSubscriptionsFromUser(String token) throws SQLException {
        SubscriptionResponse abonnementResponse = new SubscriptionResponse();

        for(UserSubscriptions us: userSubscriptionsDao.getUserSubscriptionList(loginDao.getUserId(token))) {
            abonnementResponse.addSubscriptionToList(subscriptionDao.getSubscription(us.getSubscription_id()));
            if("actief".equals(us.getStatus())) {
                abonnementResponse.addToTotalPrice(us.getPrice());
            }
        }
        return abonnementResponse;

    }

    public SubscriptionAllResponse getAllAvailableSubscriptionsForUser(String token) throws SQLException {
        SubscriptionAllResponse subscriptionAllResponse = new SubscriptionAllResponse();

        List<Subscription> list = new ArrayList<Subscription>();

        for(Subscription s: subscriptionDao.getSubscriptionList()) {
            list.add(s);
        }

        for(UserSubscriptions us: userSubscriptionsDao.getUserSubscriptionList(loginDao.getUserId(token))) {
            list.remove(subscriptionDao.getSubscription(us.getSubscription_id()));
        }

        for(Subscription s: list) {
            subscriptionAllResponse.addSubscriptionToList(s);
        }

        return subscriptionAllResponse;
    }

    public SubscriptionResponse addSubscriptionToUser(String token, int subscription_id) throws SQLException {
        SubscriptionResponse abonnementResponse = new SubscriptionResponse();

        if(userSubscriptionsDao.createUserSubscription(subscription_id, loginDao.getUserId(token))) {
            for(UserSubscriptions us: userSubscriptionsDao.getUserSubscriptionList(loginDao.getUserId(token))) {
                if(subscriptionDao.getSubscription(us.getSubscription_id()).getId() == us.getSubscription_id()) {
                    abonnementResponse.addSubscriptionToList(subscriptionDao.getSubscription(us.getSubscription_id()));
                    if("actief".equals(us.getStatus())) {
                        abonnementResponse.addToTotalPrice(us.getPrice());
                    }
                }
            }
        }
        return abonnementResponse;
    }

    public SubscriptionDetailsResponse getAbonnementDetailsForUser(String token, int id) throws SQLException {
        SubscriptionDetailsResponse subscriptionDetailsResponse;
        return subscriptionDetailsResponse = createSubscriptionDetailsResponse(token, id);
    }

    public SubscriptionDetailsResponse terminateSubscriptionFromUser(String token, int id) throws SQLException {
        SubscriptionDetailsResponse subscriptionDetailsResponse = new SubscriptionDetailsResponse();

        if(userSubscriptionsDao.terminateUserSubscription(id, loginDao.getUserId(token))) {
            subscriptionDetailsResponse = createSubscriptionDetailsResponse(token, id);
        }

        return subscriptionDetailsResponse;
    }

    public SubscriptionDetailsResponse upgradeSubscriptionForUser(String token, int id) throws SQLException {
        SubscriptionDetailsResponse subscriptionDetailsResponse = new SubscriptionDetailsResponse();

        if(userSubscriptionsDao.upgradeUserSubscription(id, loginDao.getUserId(token))) {
            subscriptionDetailsResponse = createSubscriptionDetailsResponse(token, id);
        }

        return subscriptionDetailsResponse;
    }

    private SubscriptionDetailsResponse createSubscriptionDetailsResponse(String token, int subscription_id) throws SQLException {
        SubscriptionDetailsResponse subscriptionDetailsResponse = new SubscriptionDetailsResponse();

        Subscription subscription = subscriptionDao.getSubscription(subscription_id);
        UserSubscriptions userSubscriptions = userSubscriptionsDao.getUserSubscription(subscription_id, loginDao.getUserId(token));

        subscriptionDetailsResponse.setId(subscription.getId());
        subscriptionDetailsResponse.setAanbieder(subscription.getAanbieder());
        subscriptionDetailsResponse.setDienst(subscription.getDienst());
        subscriptionDetailsResponse.setPrijs(userSubscriptions.getPrice());
        subscriptionDetailsResponse.setStartDatum(userSubscriptions.getStartDate());
        subscriptionDetailsResponse.setVerdubbeling(userSubscriptions.getDoubling());
        subscriptionDetailsResponse.setDeelbaar(userSubscriptions.isShareable());
        subscriptionDetailsResponse.setStatus(userSubscriptions.getStatus());

        return subscriptionDetailsResponse;

    }
}
