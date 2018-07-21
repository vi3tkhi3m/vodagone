package nl.han.dea.datasource;

import java.sql.SQLException;

public interface LoginDao {

    public boolean findUser(String user, String password) throws SQLException;

    public void addTokenToUser(String user, String token) throws SQLException;

    public boolean checkIfUserHasToken(String user);

    public boolean checkIfTokenExists(String token);

    public String getUserToken(String user);

    public int getUserId(String token);
}
