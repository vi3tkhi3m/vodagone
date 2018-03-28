package datasource;

import constants.NamedQueries;
import datasource.entity.UserSubscriptions;
import datasource.util.ConnectionManager;
import rest.dto.LoginRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDaoImpl implements LoginDao {

    private Connection con = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    @Override
    public boolean findUser(String user, String password) {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.FIND_USER_BY_USER_AND_PASSWORD);
            stmt.setString(1, user);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.print("Cant get user from database with user" + user + " and password "+ password + ". " + e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public void addTokenToUser(String user, String token) {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.ADD_TOKEN_TO_USER);
            stmt.setString(1, user);
            stmt.setString(2, token);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.print("Cant add token to user " + user + ". " + e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean checkIfUserHasToken(String user) {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.GET_TOKEN_FROM_USER);
            stmt.setString(1, user);
            rs = stmt.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.print("Cant get token from" + user + ". " + e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public boolean checkIfTokenExists(String token) {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.FIND_TOKEN);
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.print("Cant get token from database with token" + token + ". " + e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public String getUserToken(String user) {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.GET_TOKEN_FROM_USER);
            stmt.setString(1, user);
            rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getString("token");
            }
        } catch (SQLException e) {
            System.out.print("Cant get token from database with user" + user + ". " + e);
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public int getUserId(String token) {
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(NamedQueries.GET_ID_FROM_USER_WITH_TOKEN);
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.print("Cant get id from database with token" + token + ". " + e);
        } finally {
            closeConnection();
        }
        return 0;
    }

    public void closeConnection() {
        if(con != null) {
            try {
                con.close();
                stmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
