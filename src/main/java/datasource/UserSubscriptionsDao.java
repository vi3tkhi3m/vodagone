package datasource;

import datasource.entity.UserSubscriptions;
import store.UserSubscriptionMapper;

import javax.sql.DataSource;
import java.util.List;

public interface UserSubscriptionsDao {

    public boolean createUserSubscription(int subscription_id, int user_id);

    public UserSubscriptions getUserSubscription(int user_id, int subscription_id);

    public List<UserSubscriptions> getUserSubscriptionList(int user_id);

    public void delete(int subscription_id, int user_id);

    public void update(int subscription_id, int user_id);
}
