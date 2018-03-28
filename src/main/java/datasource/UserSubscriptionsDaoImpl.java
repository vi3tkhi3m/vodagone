package datasource;

import constants.NamedQueries;
import datasource.entity.Subscription;
import datasource.entity.UserSubscriptions;
import datasource.util.ConnectionManager;
import functions.func;
import store.UserSubscriptionMapper;
import store.impl.UserSubscriptionMapperImpl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserSubscriptionsDaoImpl implements UserSubscriptionsDao {

//    @Inject
//    private Logger logger;

    @Inject
    UserSubscriptionMapper userSubscriptionMapper;

    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public boolean createUserSubscription(int subscription_id, int user_id) {
        int price = getSubscriptionPrice(subscription_id);
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.CREATE_USER_SUBSCRIPTION);
            stmt.setInt(1, subscription_id);
            stmt.setInt(2, user_id);
            stmt.setDate(3, func.getCurrentDate());
            stmt.setInt(4, price);
            int i = stmt.executeUpdate();
            if(i == 1) {
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
    public UserSubscriptions getUserSubscription(int user_id, int subscription_id) {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.GET_USER_SUBSCRIPTIONS_BY_SUB_USR_ID);
            stmt.setInt(1, subscription_id);
            stmt.setInt(2, user_id);
            rs = stmt.executeQuery();
            if(rs.next()) {
                UserSubscriptions userSubscriptions = new UserSubscriptions();
                userSubscriptions.setId(rs.getInt("id"));
                userSubscriptions.setSubscription_id(rs.getInt("abonnement_id"));
                userSubscriptions.setUser_id(rs.getInt("gebruikers_id"));
                userSubscriptions.setStartDate(rs.getString("startDatum"));
                userSubscriptions.setDoubling(rs.getString("verdubbeling"));
                userSubscriptions.setShareable(rs.getString("deelbaar"));
                userSubscriptions.setStatus(rs.getString("status"));
                userSubscriptions.setPrice(rs.getInt("prijs"));

                userSubscriptionMapper.insert(userSubscriptions);
            }
        } catch (SQLException e) {
            System.out.print("Cant get usersubscription from database with user_id" + user_id + " and subscription_id "+ subscription_id + ". " + e);
        } finally {
            closeConnection();
        }
        return userSubscriptionMapper.find(user_id, subscription_id);
    }

    @Override
    public List<UserSubscriptions> getUserSubscriptionList(int user_id) {
        if(!userSubscriptionMapper.getUserSubscriptions().isEmpty()) {
            System.out.println("Ik heb waardes!");
            System.out.println("List Size US: " + userSubscriptionMapper.getUserSubscriptions().size());
            return userSubscriptionMapper.find(user_id);
        } else {
            try {
                System.out.println("Ik heb GEEEEEN waardes!");
                con = ConnectionManager.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_ALL_USER_SUBSCRIPTIONS_OF_USER);
                stmt.setInt(1, user_id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    UserSubscriptions userSubscriptions = new UserSubscriptions();
                    userSubscriptions.setId(rs.getInt("id"));
                    userSubscriptions.setSubscription_id(rs.getInt("abonnement_id"));
                    userSubscriptions.setUser_id(rs.getInt("gebruikers_id"));
                    userSubscriptions.setStartDate(rs.getString("startDatum"));
                    userSubscriptions.setDoubling(rs.getString("verdubbeling"));
                    userSubscriptions.setShareable(rs.getString("deelbaar"));
                    userSubscriptions.setStatus(rs.getString("status"));
                    userSubscriptions.setPrice(rs.getInt("prijs"));
                    userSubscriptionMapper.getUserSubscriptions().add(userSubscriptions);
                    System.out.println("List Size US: " + userSubscriptionMapper.getUserSubscriptions().size());
                }
            } catch (SQLException e) {
                System.out.print("Cant get usersubscription from database with user_id " + user_id+ ". " + e);
            } finally {
                closeConnection();
            }
            return userSubscriptionMapper.find(user_id);
        }
    }

    public int getSubscriptionPrice(int subscription_id) {
        int price = 0;
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.GET_SUBSCRIPTION_PRICE);
            stmt.setInt(1, subscription_id);
            rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("prijs");
            }
        } catch (SQLException e) {
            //logger.log(Level.SEVERE, "Cant get subscription price from database with subscription_id: " + subscription_id, e);
        } finally {
            closeConnection();
        }
        return price;
    }

    @Override
    public void delete(int subscription_id, int user_id) {

    }

    @Override
    public void update(int subscription_id, int user_id) {

    }

    public void closeConnection() {
        if(con != null) {
            try {
                con.close();
                stmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
