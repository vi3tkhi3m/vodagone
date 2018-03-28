//package datasource;
//
//import datasource.util.DatabaseProperties;
//import datasource.entity.Subscriber;
//import rest.dto.AbonneesResponse;
//
//import java.sql.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class AbonneesDaoImpl {
//    private Logger logger = Logger.getLogger(getClass().getName());
//
//    private DatabaseProperties databaseProperties;
//
//    private Connection connection;
//    private PreparedStatement statement;
//
//    public AbonneesDaoImpl(DatabaseProperties databaseProperties)
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
//    public AbonneesResponse getAllSubscribers(String token) {
//        AbonneesResponse abonneesResponse = new AbonneesResponse();
//
//        try {
//            connection = DriverManager.getConnection(databaseProperties.connectionString());
//            statement = connection.prepareStatement("SELECT * FROM abonnees");
//            ResultSet resultSet = statement.executeQuery();
//            while(resultSet.next()) {
//                Subscriber subscriber = new Subscriber(resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("email"));
//                abonneesResponse.addSubscriberToList(subscriber);
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
//        return abonneesResponse;
//    }
//}
