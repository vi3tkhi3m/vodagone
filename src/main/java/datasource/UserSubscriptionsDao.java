package datasource;

import domain.UserSubscriptions;

import java.util.List;

public interface UserSubscriptionsDao {

    public boolean createUserSubscription(int subscription_id, int user_id);

    public UserSubscriptions getUserSubscription(int subscription_id, int user_id);

    public List<UserSubscriptions> getUserSubscriptionList(int user_id);

    public boolean upgradeUserSubscription(int subscription_id, int user_id);

    public boolean terminateUserSubscription(int subscription_id, int user_id);

    public List<UserSubscriptions> getAllUserSubscriptions();
}
