package datasource.util;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

    private static DatabaseProperties databaseProperties = new DatabaseProperties();
    private static Connection con;

    public static Connection getConnection() {
        try {
            Class.forName(databaseProperties.driver());
            try {
                con = DriverManager.getConnection(databaseProperties.connectionString());
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        return con;
    }



}
