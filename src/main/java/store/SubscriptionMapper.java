package store;

import domain.Subscription;
import exceptions.DataMapperException;

import java.util.List;

public interface SubscriptionMapper {

    Subscription find(int subscription_id);

    void insert(Subscription subscriptionToBeInserted) throws DataMapperException;

    void update(Subscription subscriptionToBeUpdated) throws DataMapperException;

    void delete(Subscription subscriptionToBeDeleted) throws DataMapperException;

    List<Subscription> getSubscriptions();

}
