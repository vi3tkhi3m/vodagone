package services;

import datasource.*;
import datasource.entity.Subscription;
import datasource.entity.UserSubscriptions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rest.dto.AbonnementRequest;
import rest.dto.SubscriptionAllResponse;
import rest.dto.SubscriptionResponse;
import store.UserSubscriptionMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionService {

    UserSubscriptionsDaoImpl userSubscriptionsDao = new UserSubscriptionsDaoImpl();
    SubscriptionDaoImpl subscriptionDao = new SubscriptionDaoImpl();

//    @Inject
//    UserSubscriptionMapper userSubscriptionMapper;

    @Inject
    LoginDao loginDao;

    public SubscriptionResponse getAllSubscriptionsFromUser(String token) throws SQLException {
        SubscriptionResponse abonnementResponse = new SubscriptionResponse();

        for(UserSubscriptions us: userSubscriptionsDao.getUserSubscriptionList(loginDao.getUserId(token))) {
            abonnementResponse.addSubscriptionToList(subscriptionDao.getSubscription(us.getSubscription_id()));
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

//    public SubscriptionResponse addSubscriptionToUser(String token, AbonnementRequest abonnementRequest) {
//        SubscriptionResponse abonnementResponse = new SubscriptionResponse();
//
//        if(userSubscriptionsDao.createUserSubscription(abonnementRequest.getId(), loginDao.getUserId(token))) {
//            for(User)
//        }
//    }
}
