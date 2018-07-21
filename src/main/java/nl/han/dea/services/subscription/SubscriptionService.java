package nl.han.dea.services.subscription;

import nl.han.dea.datasource.*;
import nl.han.dea.domain.SubscriberSubscription;
import nl.han.dea.domain.Subscription;
import nl.han.dea.domain.UserSubscriptions;
import nl.han.dea.dtos.subscription.SubscriptionAllResponse;
import nl.han.dea.dtos.subscription.SubscriptionDetailsResponse;
import nl.han.dea.dtos.subscription.SubscriptionResponse;

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
    SubscriberSubscriptionDao subscriberSubscriptionDao;

    @Inject
    LoginDao loginDao;

    public SubscriptionResponse getAllSubscriptionsForUser(String token) throws SQLException {
        SubscriptionResponse abonnementResponse = new SubscriptionResponse();

        getAllSubscriptionsForUser(token, abonnementResponse);
        getAllSubscriptionsForSubscriber(token, abonnementResponse);

        return abonnementResponse;

    }

    private void getAllSubscriptionsForSubscriber(String token, SubscriptionResponse abonnementResponse) throws SQLException {
        for(SubscriberSubscription ss: subscriberSubscriptionDao.getAllSubscriberSubscription(loginDao.getUserId(token))) {
            abonnementResponse.addSubscriptionToList(subscriptionDao.getSubscription(userSubscriptionsDao.getUserSubscription(ss.getUserSubscription_id()).getSubscription_id()));
        }
    }

    private void getAllSubscriptionsForUser(String token, SubscriptionResponse abonnementResponse) throws SQLException {
        for(UserSubscriptions us: userSubscriptionsDao.getUserSubscriptionsOfUser(loginDao.getUserId(token))) {
            abonnementResponse.addSubscriptionToList(subscriptionDao.getSubscription(us.getSubscription_id()));
            if("actief".equals(us.getStatus())) {
                abonnementResponse.addToTotalPrice(us.getPrice());
            }
        }
    }

    public SubscriptionAllResponse getAllAvailableSubscriptionsForUser(String token) throws SQLException {
        SubscriptionAllResponse subscriptionAllResponse = new SubscriptionAllResponse();

        List<Subscription> list = new ArrayList<Subscription>();

        for(Subscription s: subscriptionDao.getSubscriptionList()) {
            list.add(s);
        }

        for(UserSubscriptions us: userSubscriptionsDao.getUserSubscriptionsOfUser(loginDao.getUserId(token))) {
            list.remove(subscriptionDao.getSubscription(us.getSubscription_id()));
        }

        for(SubscriberSubscription ss: subscriberSubscriptionDao.getAllSubscriberSubscription(loginDao.getUserId(token))) {
            list.remove(subscriptionDao.getSubscription(userSubscriptionsDao.getUserSubscription(ss.getUserSubscription_id()).getSubscription_id()));
        }

        for(Subscription s: list) {
            subscriptionAllResponse.addSubscriptionToList(s);
        }

        return subscriptionAllResponse;
    }

    public SubscriptionResponse addSubscriptionToUser(String token, int subscription_id) throws SQLException {
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();

        if(userSubscriptionsDao.createUserSubscription(subscription_id, loginDao.getUserId(token))) {
            subscriptionResponse = getAllSubscriptionsForUser(token);
        }
        return subscriptionResponse;
    }



    public SubscriptionDetailsResponse getSubscriptionDetailsForUser(String token, int id) throws SQLException {
        SubscriptionDetailsResponse subscriptionDetailsResponse = createSubscriptionDetailsResponse(token, id);
        return subscriptionDetailsResponse;
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
        UserSubscriptions userSubscriptions = new UserSubscriptions();

        if(checkIfUserHasASpecificSubscriptionNotOnHisName(token, subscription_id)) {
            userSubscriptions = getSubscriptionDetailsFromSubscriptionOwner(token, subscription_id);
        } else {
            userSubscriptions = userSubscriptionsDao.getUserSubscription(subscription_id, loginDao.getUserId(token));
        }

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

    private boolean checkIfUserHasASpecificSubscriptionNotOnHisName(String token, int subscription_id) {
        if(userSubscriptionsDao.getUserSubscription(subscription_id, loginDao.getUserId(token)) == null) {
            return true;
        }
        return false;
    }

    public UserSubscriptions getSubscriptionDetailsFromSubscriptionOwner(String token, int subscription_id) throws SQLException {
        UserSubscriptions userSubscriptions = new UserSubscriptions();
        for(SubscriberSubscription ss: subscriberSubscriptionDao.getAllSubscriberSubscription(loginDao.getUserId(token))) {
            for(UserSubscriptions us: userSubscriptionsDao.getAllUserSubscriptions()) {
                if(ss.getUserSubscription_id() == us.getId() && us.getSubscription_id() == subscription_id) {
                    userSubscriptions = us;
                }
            }
        }
        return userSubscriptions;
    }
}
