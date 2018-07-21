package nl.han.dea.datasource;

import nl.han.dea.domain.Subscription;

import java.sql.SQLException;
import java.util.List;

public interface SubscriptionDao {

    public Subscription getSubscription(int id) throws SQLException;

    public List<Subscription> getSubscriptionList();

}
