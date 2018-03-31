package datasource.impl;

import datasource.util.NamedQueries;
import datasource.SubscriptionDao;
import domain.Subscription;
import datasource.util.ConnectionManager;
import store.SubscriptionMapper;

import javax.inject.Inject;
import java.sql.*;
import java.util.List;

public class SubscriptionDaoImpl implements SubscriptionDao {

    @Inject
    private SubscriptionMapper subscriptionMapper;

    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public Subscription getSubscription(int id) throws SQLException {
        if(subscriptionMapper.find(id) != null) {
            return subscriptionMapper.find(id);
        } else {
            try {
                con = ConnectionManager.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_SUBSCRIPTION_BY_ID);
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    subscriptionMapper.insert(convertResultSetToSubsription(rs));
                }
            } catch (SQLException e) {
                System.out.print("Cant get subscription from database with ID" + id + ". " + e);
            } finally {
                closeConnection();
            }
            return subscriptionMapper.find(id);
        }
    }

    @Override
    public List<Subscription> getSubscriptionList() {
        if(!subscriptionMapper.getSubscriptions().isEmpty()) {
            return subscriptionMapper.getSubscriptions();
        } else {
            try {
                con = ConnectionManager.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_ALl_SUBSCRIPTIONS);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    subscriptionMapper.insert(convertResultSetToSubsription(rs));
                }
            } catch (SQLException e) {
                System.out.print("Cant get subscriptions from database. " + e);
            } finally {
                closeConnection();
            }
            return subscriptionMapper.getSubscriptions();
        }

    }

    public Subscription convertResultSetToSubsription(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setId(rs.getInt("id"));
        subscription.setAanbieder(rs.getString("aanbieder"));
        subscription.setDienst(rs.getString("dienst"));
        subscription.setPrijs(rs.getInt("prijs"));
        subscription.setDeelbaar(rs.getBoolean("deelbaar"));
        subscription.setDeelbaar_aantal(rs.getInt("deelbaar_aantal"));
        subscription.setVerdubbeling(rs.getBoolean("verdubbeling"));

        return subscription;
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
