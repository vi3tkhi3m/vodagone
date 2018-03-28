package datasource;

import rest.dto.LoginRequest;

import javax.sql.DataSource;

public interface LoginDao {

    public boolean findUser(String user, String password);

    public void addTokenToUser(String user, String token);

    public boolean checkIfUserHasToken(String user);

    public boolean checkIfTokenExists(String token);

    public String getUserToken(String user);

    public int getUserId(String token);
}
