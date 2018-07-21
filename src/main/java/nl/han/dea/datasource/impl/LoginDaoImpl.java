package nl.han.dea.datasource.impl;

import nl.han.dea.datasource.LoginDao;
import nl.han.dea.datasource.util.NamedQueries;
import nl.han.dea.datasource.util.ConnectionManager;

import javax.enterprise.inject.Default;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
public class LoginDaoImpl extends DaoImpl implements LoginDao {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Override
    public boolean findUser(String user, String password) {
        try {
            con = sqlConnection.getConnection();
            stmt = con.prepareStatement(NamedQueries.FIND_USER_BY_USER_AND_PASSWORD);
            stmt.setString(1, user);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Cant get user from database with user" + user + " and password "+ password + ". ", e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public void addTokenToUser(String user, String token) {
        try {
            con = sqlConnection.getConnection();
            stmt = con.prepareStatement(NamedQueries.ADD_TOKEN_TO_USER);
            stmt.setString(1, token);
            stmt.setString(2, user);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Cant add token to user " + user + ". ", e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean checkIfUserHasToken(String user) {
        try {
            con = sqlConnection.getConnection();
            stmt = con.prepareStatement(NamedQueries.GET_TOKEN_FROM_USER);
            stmt.setString(1, user);
            rs = stmt.executeQuery();
            if(rs.next()) {
                if("".equals(rs.getString("token"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Cant get token from" + user + ". ", e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public boolean checkIfTokenExists(String token) {
        try {
            con = sqlConnection.getConnection();
            stmt = con.prepareStatement(NamedQueries.FIND_TOKEN);
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Cant check if token exists with token: " + token + ". ", e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public String getUserToken(String user) {
        try {
            con = sqlConnection.getConnection();
            stmt = con.prepareStatement(NamedQueries.GET_TOKEN_FROM_USER);
            stmt.setString(1, user);
            rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getString("token");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Cant get token from database with user: " + user + ". ", e);
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public int getUserId(String token) {
        try {
            con = sqlConnection.getConnection();
            stmt = con.prepareStatement(NamedQueries.GET_ID_FROM_USER_WITH_TOKEN);
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Cant get user_id from database with token" + token + ". ", e);
        } finally {
            closeConnection();
        }
        return 0;
    }

}
