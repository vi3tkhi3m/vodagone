package nl.han.dea.datasource.impl;

import nl.han.dea.datasource.UserSubscriptionsDao;
import nl.han.dea.datasource.util.NamedQueries;
import nl.han.dea.domain.Subscription;
import nl.han.dea.domain.UserSubscriptions;
import nl.han.dea.datasource.util.ConnectionManager;
import nl.han.dea.store.SubscriptionMapper;
import nl.han.dea.store.UserSubscriptionMapper;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
public class UserSubscriptionsDaoImpl extends DaoImpl implements UserSubscriptionsDao {

    @Inject
    private UserSubscriptionMapper userSubscriptionMapper;

    @Inject
    private SubscriptionMapper subscriptionMapper;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Override
    public boolean createUserSubscription(int subscription_id, int user_id) {
        Subscription subscription = subscriptionMapper.find(subscription_id);
        String verdubbeling;
        if(subscription.isVerdubbeling() == true) {
            verdubbeling = "standaard";
        } else {
            verdubbeling = "verdubbeld";
        }
        try {
            con = sqlConnection.getConnection();
            stmt = con.prepareStatement(NamedQueries.CREATE_USER_SUBSCRIPTION);
            stmt.setInt(1, subscription_id);
            stmt.setInt(2, user_id);
            stmt.setDate(3, getCurrentDate());
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
            LOGGER.log(Level.SEVERE, "Cant add subscription to DB with user_id: " + user_id + " and subscription_id "+ subscription_id + ". ", e);
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
                con = sqlConnection.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_USER_SUBSCRIPTIONS_BY_SUB_USR_ID);
                stmt.setInt(1, subscription_id);
                stmt.setInt(2, user_id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    userSubscriptionMapper.insert(convertResultSetToUserSubscription(rs));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant get usersubscription from database with user_id" + user_id + " and subscription_id " + subscription_id + ". ", e);
            } finally {
                closeConnection();
            }
            return userSubscriptionMapper.find(user_id, subscription_id);
        }
    }

    @Override
    public UserSubscriptions getUserSubscription(int userSubscription_id) {
        if(userSubscriptionMapper.findUserSubscriptionByID(userSubscription_id) != null) {
            return userSubscriptionMapper.findUserSubscriptionByID(userSubscription_id);
        } else {
            try {
                con = sqlConnection.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_USER_SUBSCRIPTION_BY_ID);
                stmt.setInt(1, userSubscription_id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    userSubscriptionMapper.insert(convertResultSetToUserSubscription(rs));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant get usersubscription from database with id " + userSubscription_id + ". ", e);
            } finally {
                closeConnection();
            }
            return userSubscriptionMapper.findUserSubscriptionByID(userSubscription_id);
        }
    }

    @Override
    public List<UserSubscriptions> getUserSubscriptionsOfUser(int user_id) {
        if(!userSubscriptionMapper.getUserSubscriptions().isEmpty()) {
            return userSubscriptionMapper.find(user_id);
        } else {
            try {
                con = sqlConnection.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_ALL_USER_SUBSCRIPTIONS_OF_USER);
                stmt.setInt(1, user_id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    userSubscriptionMapper.insert(convertResultSetToUserSubscription(rs));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant get usersubscription from database with user_id " + user_id+ ". ", e);
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
            con = sqlConnection.getConnection();
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
            LOGGER.log(Level.SEVERE, "Cant upgrade user ubscription to DB with user_id: " + user_id + " and subscription_id "+ subscription_id + ". ", e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public boolean terminateUserSubscription(int subscription_id, int user_id) {
        try {
            con = sqlConnection.getConnection();
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
            LOGGER.log(Level.SEVERE, "Cant terminate subscription to DB with user_id: " + user_id + " and subscription_id "+ subscription_id + ". ", e);
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
                con = sqlConnection.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_ALL_USER_SUBSCRIPTIONS);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    userSubscriptionMapper.getUserSubscriptions().add(convertResultSetToUserSubscription(rs));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant get all usersubscription from database. ", e);
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

    public java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
}
