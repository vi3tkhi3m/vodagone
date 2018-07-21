package nl.han.dea.datasource.impl;

import javassist.bytecode.stackmap.TypeData;
import nl.han.dea.datasource.Dao;
import nl.han.dea.datasource.util.SQLConnection;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class DaoImpl implements Dao {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Inject
    SQLConnection sqlConnection;

    Connection con;
    PreparedStatement stmt;
    ResultSet rs;

    public void closeConnection() {
        try {
            if (con != null) con.close();
            if (stmt != null) stmt.close();
            if (rs != null) con.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Can't close connection with database. ", e);
        }
    }

}

