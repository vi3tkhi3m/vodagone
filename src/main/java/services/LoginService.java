package services;

import datasource.LoginDao;
import functions.func;

import javax.inject.Inject;

public class LoginService {

    @Inject
    private LoginDao loginDao;

    public boolean doLogin(String username, String password) {
        if(loginDao.findUser(username, password)) {
            if(loginDao.checkIfUserHasToken(username)) {
                return true;
            } else {
                String token = func.generateToken();
                while(!loginDao.checkIfTokenExists(token)) {
                    loginDao.addTokenToUser(username,token);
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public String getTokenFromUser(String user) {
        return loginDao.getUserToken(user);
    }
}
