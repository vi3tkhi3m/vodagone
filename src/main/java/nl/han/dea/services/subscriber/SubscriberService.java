package nl.han.dea.services.subscriber;

import nl.han.dea.datasource.LoginDao;
import nl.han.dea.datasource.SubscriberDao;
import nl.han.dea.domain.Subscriber;
import nl.han.dea.dtos.subscriber.SubscriberResponse;

import javax.inject.Inject;

public class SubscriberService {

    @Inject
    SubscriberDao subscriberDao;

    @Inject
    LoginDao loginDao;

    public SubscriberResponse getAllAvailableSubscribersForUser(String token) {
        SubscriberResponse subscriberResponse = new SubscriberResponse();

        for(Subscriber subscriber: subscriberDao.getAllSubscribers(loginDao.getUserId(token))) {
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
