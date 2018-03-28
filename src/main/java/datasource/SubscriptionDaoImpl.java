package datasource;

import constants.NamedQueries;
import datasource.entity.Subscription;
import datasource.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import store.SubscriptionMapper;
import store.impl.SubscriptionMapperImpl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubscriptionDaoImpl implements SubscriptionDao {

    private SubscriptionMapperImpl subscriptionMapper = new SubscriptionMapperImpl();


//    private Logger logger = new Logger();

    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public void addSubscription(String aanbieder, String dienst, int prijs) {

    }

    @Override
    public Subscription getSubscription(int id) throws SQLException {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.GET_SUBSCRIPTION_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Subscription subscription = new Subscription();
                subscription.setId(rs.getInt("id"));
                subscription.setAanbieder(rs.getString("aanbieder"));
                subscription.setDienst(rs.getString("dienst"));
                subscriptionMapper.insert(subscription);
            }
        } catch (SQLException e) {
            System.out.print("Cant get subscription from database with ID" + id + ". " + e);
        } finally {
            closeConnection();
        }
        return subscriptionMapper.find(id);
    }

    @Override
    public List<Subscription> getSubscriptionList() {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.GET_ALl_SUBSCRIPTIONS);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Subscription subscription = new Subscription();
                subscription.setId(rs.getInt("id"));
                subscription.setAanbieder(rs.getString("aanbieder"));
                subscription.setDienst(rs.getString("dienst"));
                subscriptionMapper.insert(subscription);
            }
        } catch (SQLException e) {
            System.out.print("Cant get subscriptions from database. " + e);
        } finally {
            closeConnection();
        }
        return subscriptionMapper.getSubscriptions();

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
