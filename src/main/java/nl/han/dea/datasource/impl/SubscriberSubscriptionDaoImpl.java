package nl.han.dea.datasource.impl;

import nl.han.dea.datasource.SubscriberSubscriptionDao;
import nl.han.dea.datasource.util.ConnectionManager;
import nl.han.dea.datasource.util.NamedQueries;
import nl.han.dea.domain.SubscriberSubscription;
import nl.han.dea.store.SubscriberSubscriptionMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
@ApplicationScoped
public class SubscriberSubscriptionDaoImpl extends DaoImpl implements SubscriberSubscriptionDao {

    @Inject
    private SubscriberSubscriptionMapper subscriberSubscriptionMapper;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Override
    public SubscriberSubscription getAllSubscriberSubscription(int subscriber_id, int userSubscription_id) throws SQLException {
        if(subscriberSubscriptionMapper.find(subscriber_id, userSubscription_id) != null) {
            return subscriberSubscriptionMapper.find(subscriber_id, userSubscription_id);
        } else {
            try {
                con = sqlConnection.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_SUBSCRIBERSUBSCRIPTION_BY_UID_USID);
                stmt.setInt(1, subscriber_id);
                stmt.setInt(2, userSubscription_id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    subscriberSubscriptionMapper.insert(convertResultSetToSubscriberSubscription(rs));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant get subscription from database with ID.", e);
            } finally {
                closeConnection();
            }
            return subscriberSubscriptionMapper.find(subscriber_id, userSubscription_id);
        }
    }

    @Override
    public List<SubscriberSubscription> getAllSubscriberSubscription(int subscriber_id) throws SQLException {
        if(subscriberSubscriptionMapper.find(subscriber_id) != null) {
            return subscriberSubscriptionMapper.find(subscriber_id);
        } else {
            try {
                con = sqlConnection.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_SUBSCRIBERSUBSCRIPTION_BY_USID);
                stmt.setInt(1, subscriber_id);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    subscriberSubscriptionMapper.insert(convertResultSetToSubscriberSubscription(rs));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant get subscription from database with ID.", e);
            } finally {
                closeConnection();
            }
            return subscriberSubscriptionMapper.find(subscriber_id);
        }
    }

    @Override
    public List<SubscriberSubscription> getAllSubscriberSubscription() {
        if(!subscriberSubscriptionMapper.getSubscriberSubscriptionList().isEmpty()) {
            return subscriberSubscriptionMapper.getSubscriberSubscriptionList();
        } else {
            try {
                con = sqlConnection.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_ALL_SUBSCRIBERSUBSCRIPTIONS);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    subscriberSubscriptionMapper.insert(convertResultSetToSubscriberSubscription(rs));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant get subscriptions from database. ", e);
            } finally {
                closeConnection();
            }
            return subscriberSubscriptionMapper.getSubscriberSubscriptionList();
        }
    }

    public SubscriberSubscription convertResultSetToSubscriberSubscription(ResultSet rs) throws SQLException {
        SubscriberSubscription subscriberSubscription = new SubscriberSubscription();
        subscriberSubscription.setId(rs.getInt("id"));
        subscriberSubscription.setSubscriber_id(rs.getInt("abonnee_id"));
        subscriberSubscription.setUserSubscription_id(rs.getInt("gebruikers_abonnement_id"));
        return subscriberSubscription;
    }
}
