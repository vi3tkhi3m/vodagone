package services;

import datasource.LoginDao;
import datasource.SubscriberDao;
import domain.Subscriber;
import rest.dto.SubscriberResponse;

import javax.inject.Inject;

public class SubscriberService {

    @Inject
    SubscriberDao subscriberDao;

    @Inject
    LoginDao loginDao;

    public SubscriberResponse getAllAvailableSubscribers(String token) {
        SubscriberResponse subscriberResponse = new SubscriberResponse();

        for(Subscriber subscriber: subscriberDao.getAllSubscribers()) {
            subscriberResponse.addSubscriberToList(subscriber);
        }

        return subscriberResponse;
    }

    public boolean shareSubscriptionWithSubscriber(String token, int subscription_id, int subscriber_id) {
        if(subscriberDao.shareSubscriptionWithSubscriber(loginDao.getUserId(token) ,subscription_id, subscriber_id)) {
            return true;
        }
        return false;
    }
}
