package datasource;

import datasource.entity.Subscription;

import java.sql.SQLException;
import java.util.List;

public interface SubscriptionDao {

    public void addSubscription(String aanbieder, String dienst, int prijs);

    public Subscription getSubscription(int id) throws SQLException;

    public List<Subscription> getSubscriptionList();

}
