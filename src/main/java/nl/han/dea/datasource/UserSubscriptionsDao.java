package nl.han.dea.datasource;

import nl.han.dea.domain.UserSubscriptions;

import java.util.List;

public interface UserSubscriptionsDao {

    public boolean createUserSubscription(int subscription_id, int user_id);

    public UserSubscriptions getUserSubscription(int subscription_id, int user_id);

    public UserSubscriptions getUserSubscription(int userSubscription_id);

    public List<UserSubscriptions> getUserSubscriptionsOfUser(int user_id);

    public boolean upgradeUserSubscription(int subscription_id, int user_id);

    public boolean terminateUserSubscription(int subscription_id, int user_id);

    public List<UserSubscriptions> getAllUserSubscriptions();
}
