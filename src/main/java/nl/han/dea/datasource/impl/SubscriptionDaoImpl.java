package nl.han.dea.datasource.impl;

import nl.han.dea.datasource.SubscriptionDao;
import nl.han.dea.datasource.util.NamedQueries;
import nl.han.dea.domain.Subscription;
import nl.han.dea.datasource.util.ConnectionManager;
import nl.han.dea.store.SubscriptionMapper;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
public class SubscriptionDaoImpl extends DaoImpl implements SubscriptionDao {

    @Inject
    private SubscriptionMapper subscriptionMapper;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Override
    public Subscription getSubscription(int id) throws SQLException {
        if(subscriptionMapper.find(id) != null) {
            return subscriptionMapper.find(id);
        } else {
            try {
                con = sqlConnection.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_SUBSCRIPTION_BY_ID);
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    subscriptionMapper.insert(convertResultSetToSubscription(rs));
                    System.out.println("Size: " + subscriptionMapper.getSubscriptions().size());
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant get subscription from database with ID" + id + ". ", e);
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
                con = sqlConnection.getConnection();
                stmt = con.prepareStatement(NamedQueries.GET_ALl_SUBSCRIPTIONS);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    subscriptionMapper.insert(convertResultSetToSubscription(rs));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Cant get subscriptions from database. ", e);
            } finally {
                closeConnection();
            }
            return subscriptionMapper.getSubscriptions();
        }

    }

    public Subscription convertResultSetToSubscription(ResultSet rs) throws SQLException {
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

}
