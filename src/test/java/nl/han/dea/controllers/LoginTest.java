package nl.han.dea.controllers;

import nl.han.dea.controllers.login.Login;
import nl.han.dea.dtos.login.LoginRequest;
import nl.han.dea.dtos.login.LoginResponse;
import nl.han.dea.services.login.LoginService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.SQLException;

public class LoginTest {

    @Mock
    LoginService loginService;

    @Spy
    @InjectMocks
    Login login;

    LoginRequest rq;

    @Mock
    LoginResponse loginResponse;

    @Before
    public void init() {
        login = new Login();
        rq = new LoginRequest();
        loginResponse = new LoginResponse();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLoginReturnSucces() throws SQLException {
        // init
        rq.setUser("");
        rq.setPassword("");
        Mockito.when(loginService.doLogin(rq.getUser(),rq.getPassword())).thenReturn(true);

        // run
        Mockito.doNothing().when(loginResponse).setToken("");
        Mockito.doNothing().when(loginResponse).setUser("");

        // assert
        Assert.assertEquals(200, login.login(rq).getStatus());
    }

    @Test
    public void getLoginReturnForbidden() throws SQLException {
        // init
        rq.setUser("");
        rq.setPassword("");
        Mockito.when(loginService.doLogin(rq.getUser(),rq.getPassword())).thenReturn(false);

        // run
        Mockito.doNothing().when(loginResponse).setToken("");
        Mockito.doNothing().when(loginResponse).setUser("");

        // assert
        Assert.assertEquals(403, login.login(rq).getStatus());
    }
}
