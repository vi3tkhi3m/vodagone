//package datasource;
//
//import store.SubscriptionMapper;
//import datasource.util.DatabaseProperties;
//import datasource.entity.Subscription;
//import rest.dto.SubscriptionAllResponse;
//import rest.dto.AbonnementDetailsResponse;
//import rest.dto.SubscriptionResponse;
//
//import javax.inject.Inject;
//import java.sql.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class AbonnementenDaoImpl {
//
//    private Logger logger = Logger.getLogger(getClass().getName());
//
//    private DatabaseProperties databaseProperties;
//
//    private Connection connection;
//    private PreparedStatement statement;
//
//    @Inject
//    private SubscriptionMapper subscriptionMapper;
//
//    public AbonnementenDaoImpl(DatabaseProperties databaseProperties)
//    {
//        this.databaseProperties = databaseProperties;
//        tryLoadJdbcDriver(databaseProperties);
//    }
//
//    private void tryLoadJdbcDriver(DatabaseProperties databaseProperties) {
//        try {
//            Class.forName(databaseProperties.driver());
//        } catch (ClassNotFoundException e) {
//            logger.log(Level.SEVERE, "Can't load JDBC Driver " + databaseProperties.driver(), e);
//        }
//    }
//
//    public SubscriptionResponse getAllUserSubscriptions(String token) {
//        SubscriptionResponse abonnementResponse = new SubscriptionResponse();
//
//        try {
//            if(subscriptionMapper.getSubscription().isEmpty()) {
//                connection = DriverManager.getConnection(databaseProperties.connectionString());
//                statement = connection.prepareStatement("SELECT * FROM abonnementen WHERE id IN \n" +
//                        "(SELECT ga.abonnement_id FROM gebruikers_abonnementen ga " +
//                        "INNER JOIN gebruikers g ON ga.gebruikers_id = g.id WHERE g.token = ?)");
//                statement.setString(1, token);
//                ResultSet resultSet = statement.executeQuery();
//                while (resultSet.next()) {
//                    Subscription subscription = new Subscription(resultSet.getInt("id"),
//                            resultSet.getString("aanbieder"),
//                            resultSet.getString("dienst"));
//                    subscriptionMapper.insert(subscription);
//                }
//                abonnementResponse.setTotalPrice(100);
//            } else {
//                for(Subscription s: subscriptionMapper.getSubscription())
//                abonnementResponse.
//            }
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
//        } finally {
//            try{
//                if(statement!=null)
//                    statement.close();
//            }catch(SQLException se2){
//                se2.printStackTrace();
//            }
//            try{
//                if(connection!=null)
//                    connection.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//        return abonnementResponse;
//    }
//
//    public SubscriptionAllResponse getAllAvailableSubscriptionsForUser(String token) {
//        SubscriptionAllResponse abonnementAllResponse = new SubscriptionAllResponse();
//
//        try {
//            connection = DriverManager.getConnection(databaseProperties.connectionString());
//            statement = connection.prepareStatement("SELECT * FROM abonnementen WHERE id NOT IN\n" +
//                    "                    (SELECT ga.abonnement_id FROM gebruikers_abonnementen ga\n" +
//                    "                    INNER JOIN gebruikers g ON ga.gebruikers_id = g.id WHERE g.token = ?)\n");
//            statement.setString(1, token);
//            ResultSet resultSet = statement.executeQuery();
//            while(resultSet.next()) {
//                Subscription subscription = new Subscription(resultSet.getInt("id"),
//                        resultSet.getString("aanbieder"),
//                        resultSet.getString("dienst"));
//                abonnementAllResponse.addSubscriptionToList(subscription);
//            }
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
//        } finally {
//            try{
//                if(statement!=null)
//                    statement.close();
//            }catch(SQLException se2){
//                se2.printStackTrace();
//            }
//            try{
//                if(connection!=null)
//                    connection.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//        return abonnementAllResponse;
//    }
//
//    public SubscriptionResponse addSubscriptionToUser(String token, int subscriptionID) {
//        SubscriptionResponse abonnementResponse = new SubscriptionResponse();
//        int id = getUserIDFromToken(token);
//        int price = getSubscriptionPrice(subscriptionID);
//
//        try {
//            connection = DriverManager.getConnection(databaseProperties.connectionString());
//            statement = connection.prepareStatement("INSERT INTO gebruikers_abonnementen " +
//                    "VALUES (NULL,?,?,?,\"standaard\",\"nee\",\"actief\", ?)");
//            statement.setInt(1, subscriptionID);
//            statement.setInt(2, id);
//            statement.setDate(3, getCurrentDate());
//            statement.setInt(4, price);
//            statement.executeUpdate();
//            abonnementResponse = getAllUserSubscriptions(token);
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
//        } finally {
//            try{
//                if(statement!=null)
//                    statement.close();
//            }catch(SQLException se2){
//                se2.printStackTrace();
//            }
//            try{
//                if(connection!=null)
//                    connection.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//        return abonnementResponse;
//
//    }
//
//    private int getUserIDFromToken(String token) {
//        try {
//            connection = DriverManager.getConnection(databaseProperties.connectionString());
//            statement = connection.prepareStatement("SELECT id FROM gebruikers WHERE token = ?");
//            statement.setString(1, token);
//            ResultSet resultSet = statement.executeQuery();
//            if(resultSet.next()) {
//                return resultSet.getInt("id");
//            }
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
//        }
//        return -1;
//    }
//
//    public AbonnementDetailsResponse getAbonnementDetailsForUser(String token, int id) {
//        AbonnementDetailsResponse abonnementDetailsResponse = new AbonnementDetailsResponse();
//
//        try {
//            connection = DriverManager.getConnection(databaseProperties.connectionString());
//            statement = connection.prepareStatement("SELECT a.id, a.aanbieder, a.dienst, ga.prijs, ga.startDatum, ga.verdubbeling, ga.deelbaar, ga.status  " +
//                    "FROM gebruikers_abonnementen ga \n" +
//                    "INNER JOIN abonnementen a ON a.id = ga.abonnement_id \n" +
//                    "INNER JOIN gebruikers g ON ga.gebruikers_id = g.id\n" +
//                    "WHERE g.token = ? AND ga.abonnement_id = ?");
//            statement.setString(1, token);
//            statement.setInt(2, id);
//            ResultSet resultSet = statement.executeQuery();
//            if(resultSet.next()) {
//                abonnementDetailsResponse.setId(resultSet.getInt("id"));
//                abonnementDetailsResponse.setAanbieder(resultSet.getString("aanbieder"));
//                abonnementDetailsResponse.setDienst(resultSet.getString("dienst"));
//                abonnementDetailsResponse.setPrijs(resultSet.getString("prijs"));
//                abonnementDetailsResponse.setStartDatum(resultSet.getString("startDatum"));
//                abonnementDetailsResponse.setVerdubbeling(resultSet.getString("verdubbeling"));
//                abonnementDetailsResponse.setDeelbaar(resultSet.getBoolean("deelbaar"));
//                abonnementDetailsResponse.setStatus(resultSet.getString("status"));
//            }
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
//        } finally {
//            try{
//                if(statement!=null)
//                    statement.close();
//            }catch(SQLException se2){
//                se2.printStackTrace();
//            }
//            try{
//                if(connection!=null)
//                    connection.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//        return abonnementDetailsResponse;
//    }
//
//    public AbonnementDetailsResponse terminateSubscriptionFromUser(String token, int id) {
//        AbonnementDetailsResponse abonnementDetailsResponse = new AbonnementDetailsResponse();
//        int gebruikers_id = getUserIDFromToken(token);
//        try {
//            connection = DriverManager.getConnection(databaseProperties.connectionString());
//            statement = connection.prepareStatement("UPDATE gebruikers_abonnementen " +
//                    "SET status = \"opgezegd\" WHERE gebruikers_id = ? AND abonnement_id = ?");
//            statement.setInt(1, gebruikers_id);
//            statement.setInt(2, id);
//            statement.executeUpdate();
//            abonnementDetailsResponse = getAbonnementDetailsForUser(token, id);
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
//        } finally {
//            try{
//                if(statement!=null)
//                    statement.close();
//            }catch(SQLException se2){
//                se2.printStackTrace();
//            }
//            try{
//                if(connection!=null)
//                    connection.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//        return abonnementDetailsResponse;
//    }
//
//    public AbonnementDetailsResponse upgradeSubscriptionForUser(String token, int id) {
//        AbonnementDetailsResponse abonnementDetailsResponse = new AbonnementDetailsResponse();
//        int gebruikers_id = getUserIDFromToken(token);
//        int prijs_verdubbeld = getSubscriptionPrice(id) * 2;
//        try {
//            connection = DriverManager.getConnection(databaseProperties.connectionString());
//            statement = connection.prepareStatement("UPDATE gebruikers_abonnementen " +
//                    "SET verdubbeling = \"verdubbeld\", prijs = ? WHERE gebruikers_id = ? AND abonnement_id = ?");
//            statement.setInt(1, prijs_verdubbeld);
//            statement.setInt(2, gebruikers_id);
//            statement.setInt(3, id);
//            statement.executeUpdate();
//            abonnementDetailsResponse = getAbonnementDetailsForUser(token, id);
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
//        } finally {
//            try{
//                if(statement!=null)
//                    statement.close();
//            }catch(SQLException se2){
//                se2.printStackTrace();
//            }
//            try{
//                if(connection!=null)
//                    connection.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//        return abonnementDetailsResponse;
//    }
//
//    public int getSubscriptionPrice(int subscription_id) {
//        int price = 0;
//        try {
//            connection = DriverManager.getConnection(databaseProperties.connectionString());
//            statement = connection.prepareStatement("SELECT prijs FROM abonnementen WHERE id = ?");
//            statement.setInt(1, subscription_id);
//            ResultSet resultSet = statement.executeQuery();
//            if(resultSet.next()) {
//                return resultSet.getInt("prijs");
//            }
//        } catch (SQLException e) {
//            logger.log(Level.SEVERE, "Error communicating with database " + databaseProperties.connectionString(), e);
//        } finally {
//            try{
//                if(statement!=null)
//                    statement.close();
//            }catch(SQLException se2){
//                se2.printStackTrace();
//            }
//            try{
//                if(connection!=null)
//                    connection.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
//        return price;
//    }
//}
