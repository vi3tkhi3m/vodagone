package store;

import datasource.entity.UserSubscriptions;
import exceptions.DataMapperException;

import java.util.List;
import java.util.Optional;

public interface UserSubscriptionMapper {

    UserSubscriptions find(int subscription_id, int user_id);

    List<UserSubscriptions> find(int user_id);

    void insert(UserSubscriptions userSubscriptionToBeInserted) throws DataMapperException;

    void update(UserSubscriptions userSubscriptionToBeUpdated) throws DataMapperException;

    void delete(UserSubscriptions userSubscriptionToBeDeleted) throws DataMapperException;

    List<UserSubscriptions> getUserSubscriptions();
}