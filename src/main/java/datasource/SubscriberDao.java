package datasource;

import domain.Subscriber;

import java.util.List;

public interface SubscriberDao {

    public List<Subscriber> getAllSubscribers();

    public boolean shareSubscriptionWithSubscriber(int user_id, int subscription_id, int subscriber_id);
}
