package nl.han.dea.datasource.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLConnection {
    Connection getConnection() throws SQLException;
}
