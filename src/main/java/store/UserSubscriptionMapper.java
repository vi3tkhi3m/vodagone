package store;

import domain.UserSubscriptions;
import exceptions.DataMapperException;

import java.util.List;

public interface UserSubscriptionMapper {

    UserSubscriptions find(int subscription_id, int user_id);

    List<UserSubscriptions> find(int user_id);

    void insert(UserSubscriptions userSubscriptionToBeInserted) throws DataMapperException;

    void update(UserSubscriptions userSubscriptionToBeUpdated) throws DataMapperException;

    void delete(UserSubscriptions userSubscriptionToBeDeleted) throws DataMapperException;

    List<UserSubscriptions> getUserSubscriptions();
}
