package datasource.impl;

import datasource.util.NamedQueries;
import datasource.SubscriberDao;
import domain.Subscriber;
import datasource.util.ConnectionManager;
import rest.dto.SubscriberResponse;
import store.SubscriberMapper;
import store.UserSubscriptionMapper;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SubscriberDaoImpl implements SubscriberDao {

    @Inject
    SubscriberMapper subscriberMapper;

    @Inject
    UserSubscriptionMapper userSubscriptionMapper;

    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public List<Subscriber> getAllSubscribers() {
        SubscriberResponse subscriberResponse = new SubscriberResponse();

        if(!subscriberMapper.getSubscribers().isEmpty()) {
            System.out.println("Er zijn subscribers!");
            return subscriberMapper.getSubscribers();
        } else {
            try {
                con = ConnectionManager.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_ALL_SUBSCRIBERS);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Subscriber subscriber = new Subscriber();
                    subscriber.setId(rs.getInt("id"));
                    subscriber.setName(rs.getString("name"));
                    subscriber.setEmail(rs.getString("email"));
                    subscriberMapper.insert(subscriber);
                    System.out.println("Subscriber toegevoegd!");
                }
            } catch (SQLException e) {
                System.out.print("Cant get subscriptions from database. " + e);
            } finally {
                closeConnection();
            }
            return subscriberMapper.getSubscribers();
        }
    }

    @Override
    public boolean shareSubscriptionWithSubscriber(int user_id, int subscription_id, int subscriber_id) {
        int s_id = userSubscriptionMapper.find(subscription_id, user_id).getId();
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.SHARE_SUBSCRIPTION_WITH_SUBSCRIBER);
            stmt.setInt(1, subscriber_id);
            stmt.setInt(2, s_id);
            int i = stmt.executeUpdate();
            if(i == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.print("Cant share subscription_id" + subscriber_id + " with subscruber_id "+ subscriber_id + ". " + e);
        } finally {
            closeConnection();
        }
        return false;
    }

    public void closeConnection() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) { /* ignored */}
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) { /* ignored */}
        }
    }
}
