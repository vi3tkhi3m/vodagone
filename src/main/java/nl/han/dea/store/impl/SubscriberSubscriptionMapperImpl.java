package nl.han.dea.store.impl;

import nl.han.dea.domain.SubscriberSubscription;
import nl.han.dea.exceptions.DataMapperException;
import nl.han.dea.store.SubscriberSubscriptionMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.List;

@Default
@ApplicationScoped
public class SubscriberSubscriptionMapperImpl implements SubscriberSubscriptionMapper{

    private List<SubscriberSubscription> subscriberSubscriptions = new ArrayList<>();

    @Override
    public SubscriberSubscription find(int subscriber_id, int userSubscription_id) {
        for (final SubscriberSubscription ss : this.getSubscriberSubscriptionList()) {
            if (ss.getSubscriber_id() == subscriber_id && ss.getUserSubscription_id() == userSubscription_id) {
                return ss;
            }
        }
        return null;
    }

    @Override
    public List<SubscriberSubscription> find(int subscriber_id) {
        List<SubscriberSubscription> subscriberSubscriptionsList = new ArrayList<>();

        for (final SubscriberSubscription ss : this.getSubscriberSubscriptionList()) {
            if (ss.getSubscriber_id() == subscriber_id) {
                subscriberSubscriptionsList.add(ss);
            }
        }
        return subscriberSubscriptionsList;
    }

    @Override
    public void insert(SubscriberSubscription subscriberSubscriptionToBeInserted) throws DataMapperException {
        if (!this.getSubscriberSubscriptionList().contains(subscriberSubscriptionToBeInserted)) {
            this.getSubscriberSubscriptionList().add(subscriberSubscriptionToBeInserted);
        } else {
            throw new DataMapperException("SubscriberSubscription [" + subscriberSubscriptionToBeInserted.getId() + "] already exists");
        }
    }

    @Override
    public List<SubscriberSubscription> getSubscriberSubscriptionList() {
        return subscriberSubscriptions;
    }
}
