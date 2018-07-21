package nl.han.dea.datasource.impl;

import nl.han.dea.datasource.SubscriberDao;
import nl.han.dea.datasource.util.NamedQueries;
import nl.han.dea.domain.Subscriber;
import nl.han.dea.datasource.util.ConnectionManager;
import nl.han.dea.domain.SubscriberSubscription;
import nl.han.dea.dtos.subscriber.SubscriberResponse;
import nl.han.dea.store.SubscriberMapper;
import nl.han.dea.store.SubscriberSubscriptionMapper;
import nl.han.dea.store.SubscriptionMapper;
import nl.han.dea.store.UserSubscriptionMapper;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
public class SubscriberDaoImpl extends DaoImpl implements SubscriberDao {

    @Inject
    SubscriberMapper subscriberMapper;

    @Inject
    SubscriptionMapper subscriptionMapper;

    @Inject
    UserSubscriptionMapper userSubscriptionMapper;

    @Inject
    SubscriberSubscriptionMapper subscriberSubscriptionMapper;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Override
    public List<Subscriber> getAllSubscribers(int user_id) {
        SubscriberResponse subscriberResponse = new SubscriberResponse();

        if(!subscriberMapper.getSubscribers().isEmpty()) {
            return subscriberMapper.getSubscribers();
        } else {
            try {
                con = sqlConnection.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_ALL_SUBSCRIBERS);
                stmt.setInt(1, user_id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    Subscriber subscriber = new Subscriber();
                    subscriber.setId(rs.getInt("id"));
                    subscriber.setName(rs.getString("name"));
                    subscriber.setEmail(rs.getString("email"));
                    subscriberMapper.insert(subscriber);
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant get subscribers from database. ", e);
            } finally {
                closeConnection();
            }
            return subscriberMapper.getSubscribers();
        }
    }

    @Override
    public boolean shareSubscriptionWithSubscriber(int user_id, int subscription_id, int subscriber_id) {
        int s_id = userSubscriptionMapper.find(subscription_id, user_id).getId();
        if(!isUserSubscriptionAlreadySharedWithUser(subscriber_id, s_id)) {
            if(doesUserSubscriptionReachedMaxSharesCount(user_id, subscription_id, s_id)) {
                if(!doesUserAlreadyHaveSubscriptionThatsBeingShared(subscriber_id, subscription_id)) {
                    try {
                        con = sqlConnection.getConnection();
                        stmt = con.prepareStatement(NamedQueries.SHARE_SUBSCRIPTION_WITH_SUBSCRIBER);
                        stmt.setInt(1, subscriber_id);
                        stmt.setInt(2, s_id);
                        int i = stmt.executeUpdate();
                        if(i == 1) {
                            SubscriberSubscription subscriberSubscription = new SubscriberSubscription();
                            subscriberSubscription.setSubscriber_id(subscriber_id);
                            subscriberSubscription.setUserSubscription_id(s_id);
                            subscriberSubscriptionMapper.insert(subscriberSubscription);
                            return true;
                        }
                    } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, "Cant share subscription_id" + subscriber_id + " with subscruber_id "+ subscriber_id + ". ", e);
                    } finally {
                        closeConnection();
                    }
                }
            }
        }
        return false;
    }

    public boolean doesUserSubscriptionReachedMaxSharesCount(int user_id, int subscription_id, int us_id) {
        int shares_count = subscriptionMapper.find(userSubscriptionMapper.find(subscription_id, user_id).getSubscription_id()).getDeelbaar_aantal();
        try {
            con = sqlConnection.getConnection();
            stmt = con.prepareStatement(NamedQueries.COUNT_SHARES_OF_USER_SUBSCRIPTION);
            stmt.setInt(1, us_id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if(rs.getInt("count") < shares_count) {
                    return true;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Cant get count of doesUserSubscriptionReachedMaxSharesCount.", e);
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean isUserSubscriptionAlreadySharedWithUser(int user_id, int userSubscription_id) {
        try {
            con = sqlConnection.getConnection();
            stmt = con.prepareStatement(NamedQueries.CHECK_IF_USER_SUBSCRIPTION_IS_ALREADY_SHARED);
            stmt.setInt(1, user_id);
            stmt.setInt(2, userSubscription_id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if(rs.getInt("count") > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Cant get count of isUserSubscriptionAlreadySharedWithUser.", e);
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean doesUserAlreadyHaveSubscriptionThatsBeingShared(int user_id, int subscription_id) {
        try {
            con = sqlConnection.getConnection();
            stmt = con.prepareStatement(NamedQueries.CHECK_IF_USER_ALREADY_HAS_SUBSCRIPTION_WITH_ID);
            stmt.setInt(1, user_id);
            stmt.setInt(2, subscription_id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if(rs.getInt("count") > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Cant get count of isUserSubscriptionAlreadySharedWithUser.", e);
        } finally {
            closeConnection();
        }
        return false;
    }
}
