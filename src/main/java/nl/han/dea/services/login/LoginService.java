package nl.han.dea.services.login;

import nl.han.dea.datasource.LoginDao;
import nl.han.dea.services.token.TokenService;

import javax.inject.Inject;
import java.sql.SQLException;

public class LoginService {

    @Inject
    private LoginDao loginDao;

    @Inject
    private TokenService tokenService;

    public boolean doLogin(String username, String password) throws SQLException {
        if(loginDao.findUser(username, password)) {
            if(loginDao.checkIfUserHasToken(username)) {
                return true;
            } else {
                String token = tokenService.generateToken();
                if (checkIfTokenExists(username, token)) return true;
            }
        }
        return false;
    }

    private boolean checkIfTokenExists(String username, String token) throws SQLException {
        while(!loginDao.checkIfTokenExists(token)) {
            loginDao.addTokenToUser(username,token);
            return true;
        }
        return false;
    }

    public String getTokenFromUser(String user) {
        return loginDao.getUserToken(user);
    }
}
