package nl.han.dea.store.impl;

import nl.han.dea.domain.Subscriber;
import nl.han.dea.exceptions.DataMapperException;
import nl.han.dea.store.SubscriberMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Default
@ApplicationScoped
public class SubscriberMapperImpl implements SubscriberMapper {

    private List<Subscriber> subscribers = new ArrayList<>();

    @Override
    public Optional<Subscriber> find(int subscriber_id) {
        for (final Subscriber subscriber : this.getSubscribers()) {
            if (subscriber.getId() == subscriber_id) {
                return Optional.of(subscriber);
            }
        }
        return Optional.empty();
    }

    @Override
    public void insert(Subscriber subscriberToBeInserted) throws DataMapperException {
        if (!this.getSubscribers().contains(subscriberToBeInserted)) {
            this.getSubscribers().add(subscriberToBeInserted);

        } else {
            throw new DataMapperException("Subscription [" + subscriberToBeInserted.getId() + "] already exists");
        }
    }

    @Override
    public void update(Subscriber subscriberToBeUpdated) throws DataMapperException {
        if (this.getSubscribers().contains(subscriberToBeUpdated)) {
            final int index = this.getSubscribers().indexOf(subscriberToBeUpdated);
            this.getSubscribers().set(index, subscriberToBeUpdated);

        } else {
            throw new DataMapperException("Subscription [" + subscriberToBeUpdated.getId() + "] is not found");
        }
    }

    @Override
    public void delete(Subscriber subscriberToBeDeleted) throws DataMapperException {
        if (this.getSubscribers().contains(subscriberToBeDeleted)) {
            this.getSubscribers().remove(subscriberToBeDeleted);

        } else {
            throw new DataMapperException("Subscription [" + subscriberToBeDeleted.getId() + "] is not found");
        }
    }

    public List<Subscriber> getSubscribers() {
        return this.subscribers;
    }
}
