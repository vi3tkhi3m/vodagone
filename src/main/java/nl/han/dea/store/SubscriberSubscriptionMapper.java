package nl.han.dea.store;

import nl.han.dea.domain.SubscriberSubscription;
import nl.han.dea.exceptions.DataMapperException;

import java.util.List;

public interface SubscriberSubscriptionMapper {

    SubscriberSubscription find(int subscriber_id, int userSubscription_id);

    List<SubscriberSubscription> find(int subscriber_id);

    void insert(SubscriberSubscription subscriber_id) throws DataMapperException;

    List<SubscriberSubscription> getSubscriberSubscriptionList();
}
