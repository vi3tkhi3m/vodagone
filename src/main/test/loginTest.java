package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rest.Login;
import rest.dto.LoginRequest;

import javax.ws.rs.core.Response;

public class loginTest {

    LoginRequest rq;
    Login login;

    @Before
    public void init() {
        rq = new LoginRequest();
        login = new Login();
    }

    @Test
    public void testEmptyRequestLogin() {

        Response resp = login.login(rq);

        Assert.assertEquals(403, resp.getStatus());

    }

}
