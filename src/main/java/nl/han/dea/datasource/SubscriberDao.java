package nl.han.dea.datasource;

import nl.han.dea.domain.Subscriber;

import java.util.List;

public interface SubscriberDao {

    public List<Subscriber> getAllSubscribers(int user_id);

    public boolean shareSubscriptionWithSubscriber(int user_id, int subscription_id, int subscriber_id);
}
