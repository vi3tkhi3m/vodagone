package nl.han.dea.store.impl;

import nl.han.dea.domain.Subscription;
import nl.han.dea.exceptions.DataMapperException;
import nl.han.dea.store.SubscriptionMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.List;

@Default
@ApplicationScoped
public class SubscriptionMapperImpl implements SubscriptionMapper {

    private List<Subscription> subscriptions = new ArrayList<>();

    @Override
    public Subscription find(int subscription_id) {
        for (final Subscription subscription : this.getSubscriptions()) {
            if (subscription.getId() == subscription_id) {
                return subscription;
            }
        }
        return null;
    }

    @Override
    public void insert(Subscription subscriptionToBeInserted) throws DataMapperException {
        if (!this.getSubscriptions().contains(subscriptionToBeInserted)) {
            this.getSubscriptions().add(subscriptionToBeInserted);

        } else {
            throw new DataMapperException("Subscription already [" + subscriptionToBeInserted.getId() + "] exists");
        }
    }

    @Override
    public void update(Subscription subscriptionToBeUpdated) throws DataMapperException {
        if (this.getSubscriptions().contains(subscriptionToBeUpdated)) {
            final int index = this.getSubscriptions().indexOf(subscriptionToBeUpdated);
            this.getSubscriptions().set(index, subscriptionToBeUpdated);

        } else {
            throw new DataMapperException("Subscription [" + subscriptionToBeUpdated.getId() + "] is not found");
        }
    }

    @Override
    public void delete(Subscription subscriptionToBeDeleted) throws DataMapperException {
        if (this.getSubscriptions().contains(subscriptionToBeDeleted)) {
            this.getSubscriptions().remove(subscriptionToBeDeleted);

        } else {
            throw new DataMapperException("Subscription [" + subscriptionToBeDeleted.getId() + "] is not found");
        }
    }

    @Override
    public List<Subscription> getSubscriptions() {
        return this.subscriptions;
    }

}
