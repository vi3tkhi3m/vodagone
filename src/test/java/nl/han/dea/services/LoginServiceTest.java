package nl.han.dea.services;

import nl.han.dea.datasource.LoginDao;
import nl.han.dea.services.login.LoginService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.SQLException;

public class LoginServiceTest {

    @Mock
    private LoginDao loginDao;

    @Spy
    @InjectMocks
    private LoginService loginService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void userExistsAndHasToken() throws SQLException {
        // init
        Mockito.when(loginDao.findUser("khiem", "test")).thenReturn(true);
        Mockito.when(loginDao.checkIfUserHasToken("khiem")).thenReturn(true);

        // assert
        Assert.assertEquals(true, loginService.doLogin("khiem", "test"));
    }

    @Test
    public void userDoesNotExist() throws SQLException {
        // init
        Mockito.when(loginDao.findUser("khiem1", "")).thenReturn(false);

        // assert
        Assert.assertEquals(false, loginService.doLogin("", ""));
    }

}
