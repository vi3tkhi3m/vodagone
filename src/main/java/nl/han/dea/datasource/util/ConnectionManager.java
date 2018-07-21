package nl.han.dea.datasource.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager implements SQLConnection {

    private final Logger logger = Logger.getLogger(ConnectionManager.class.getName());
    private DatabaseProperties databaseProperties = new DatabaseProperties();

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(databaseProperties.driver());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Can't load JDBC Driver " + databaseProperties.driver(), e);
        }
        return DriverManager.getConnection(databaseProperties.connectionString());
    }
}
