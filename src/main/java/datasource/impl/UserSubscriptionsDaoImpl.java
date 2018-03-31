package datasource.impl;

import datasource.util.NamedQueries;
import datasource.UserSubscriptionsDao;
import domain.Subscription;
import domain.UserSubscriptions;
import datasource.util.ConnectionManager;
import functions.func;
import store.SubscriptionMapper;
import store.UserSubscriptionMapper;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserSubscriptionsDaoImpl implements UserSubscriptionsDao {

    @Inject
    private UserSubscriptionMapper userSubscriptionMapper;

    @Inject
    private SubscriptionMapper subscriptionMapper;

    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public boolean createUserSubscription(int subscription_id, int user_id) {
        Subscription subscription = subscriptionMapper.find(subscription_id);
        String verdubbeling;
        System.out.print("subscription deelbaar :" + subscription.isDeelbaar());
        if(subscription.isVerdubbeling() == true) {
            verdubbeling = "standaard";
        } else {
            verdubbeling = "verdubbeld";
        }
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.CREATE_USER_SUBSCRIPTION);
            stmt.setInt(1, subscription_id);
            stmt.setInt(2, user_id);
            stmt.setDate(3, func.getCurrentDate());
            stmt.setString(4, verdubbeling);
            stmt.setBoolean(5, subscription.isDeelbaar());
            stmt.setString(6, "actief");
            stmt.setInt(7, subscription.getPrijs());

            int i = stmt.executeUpdate();
            if(i == 1) {
                this.getUserSubscription(subscription_id, user_id);
                return true;
            }
        } catch (SQLException e) {
            System.out.print("Cant add subscription to DB with user_id: " + user_id + " and subscription_id "+ subscription_id + ". " + e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public UserSubscriptions getUserSubscription(int subscription_id, int user_id) {
        if(userSubscriptionMapper.find(subscription_id, user_id) != null) {
            return userSubscriptionMapper.find(subscription_id, user_id);
        } else {
            try {
                con = ConnectionManager.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_USER_SUBSCRIPTIONS_BY_SUB_USR_ID);
                stmt.setInt(1, subscription_id);
                stmt.setInt(2, user_id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    userSubscriptionMapper.insert(convertResultSetToUserSubscription(rs));
                }
            } catch (SQLException e) {
                System.out.print("Cant get usersubscription from database with user_id" + user_id + " and subscription_id " + subscription_id + ". " + e);
            } finally {
                closeConnection();
            }
            return userSubscriptionMapper.find(user_id, subscription_id);
        }
    }

    @Override
    public List<UserSubscriptions> getUserSubscriptionList(int user_id) {
        if(!userSubscriptionMapper.getUserSubscriptions().isEmpty()) {
            return userSubscriptionMapper.find(user_id);
        } else {
            try {
                con = ConnectionManager.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_ALL_USER_SUBSCRIPTIONS_OF_USER);
                stmt.setInt(1, user_id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    userSubscriptionMapper.insert(convertResultSetToUserSubscription(rs));
                }
            } catch (SQLException e) {
                System.out.print("Cant get usersubscription from database with user_id " + user_id+ ". " + e);
            } finally {
                closeConnection();
            }
            return userSubscriptionMapper.find(user_id);
        }
    }

    @Override
    public boolean upgradeUserSubscription(int subscription_id, int user_id) {
        int priceDoubled = (int) (userSubscriptionMapper.find(subscription_id, user_id).getPrice() * 1.5);
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.UPGRADE_USER_SUBSCRIPTION);
            stmt.setInt(1, priceDoubled);
            stmt.setInt(2, user_id);
            stmt.setInt(3, subscription_id);;
            int i = stmt.executeUpdate();
            if(i == 1) {
                UserSubscriptions userSubscriptions = userSubscriptionMapper.find(subscription_id, user_id);
                userSubscriptions.setPrice(priceDoubled);
                userSubscriptions.setDoubling("verdubbeld");
                userSubscriptionMapper.update(userSubscriptions);
                return true;
            }
        } catch (SQLException e) {
            System.out.print("Cant upgrade user ubscription to DB with user_id: " + user_id + " and subscription_id "+ subscription_id + ". " + e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public boolean terminateUserSubscription(int subscription_id, int user_id) {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.TERMINATE_USER_SUBSCRIPTION);
            stmt.setInt(1, user_id);
            stmt.setInt(2, subscription_id);;
            int i = stmt.executeUpdate();
            if(i == 1) {
                UserSubscriptions userSubscriptions = userSubscriptionMapper.find(subscription_id, user_id);
                userSubscriptions.setStatus("opgezegd");
                userSubscriptionMapper.update(userSubscriptions);
                return true;
            }
        } catch (SQLException e) {
            System.out.print("Cant add subscription to DB with user_id: " + user_id + " and subscription_id "+ subscription_id + ". " + e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public List<UserSubscriptions> getAllUserSubscriptions() {
        if(!userSubscriptionMapper.getUserSubscriptions().isEmpty()) {
            return userSubscriptionMapper.getUserSubscriptions();
        } else {
            try {
                con = ConnectionManager.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_ALL_USER_SUBSCRIPTIONS);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    userSubscriptionMapper.getUserSubscriptions().add(convertResultSetToUserSubscription(rs));
                }
            } catch (SQLException e) {
                System.out.print("Cant get all usersubscription from database. " + e);
            } finally {
                closeConnection();
            }
            return userSubscriptionMapper.getUserSubscriptions();
        }
    }

    public UserSubscriptions convertResultSetToUserSubscription(ResultSet rs) throws SQLException {
        UserSubscriptions userSubscriptions = new UserSubscriptions();
        userSubscriptions.setId(rs.getInt("id"));
        userSubscriptions.setSubscription_id(rs.getInt("abonnement_id"));
        userSubscriptions.setUser_id(rs.getInt("gebruikers_id"));
        userSubscriptions.setStartDate(rs.getString("startDatum"));
        userSubscriptions.setDoubling(rs.getString("verdubbeling"));
        userSubscriptions.setShareable(rs.getBoolean("deelbaar"));
        userSubscriptions.setStatus(rs.getString("status"));
        userSubscriptions.setPrice(rs.getInt("prijs"));

        return userSubscriptions;
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
