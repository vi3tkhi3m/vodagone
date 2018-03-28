package store;

import datasource.entity.Subscriber;
import datasource.entity.Subscription;
import exceptions.DataMapperException;

import java.util.List;
import java.util.Optional;

public interface SubscriptionMapper {

    Subscription find(int subscription_id);

    void insert(Subscription subscriptionToBeInserted) throws DataMapperException;

    void update(Subscription subscriptionToBeUpdated) throws DataMapperException;

    void delete(Subscription subscriptionToBeDeleted) throws DataMapperException;

    List<Subscription> getSubscriptions();

}
