package nl.han.dea.datasource;

import nl.han.dea.domain.SubscriberSubscription;

import java.sql.SQLException;
import java.util.List;

public interface SubscriberSubscriptionDao {

    public SubscriberSubscription getAllSubscriberSubscription(int subscriber_id, int userSubscription_id) throws SQLException;

    public List<SubscriberSubscription> getAllSubscriberSubscription(int subscriber_id) throws SQLException;

    public List<SubscriberSubscription> getAllSubscriberSubscription();
}
