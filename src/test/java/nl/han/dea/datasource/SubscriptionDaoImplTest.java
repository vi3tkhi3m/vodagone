package nl.han.dea.datasource;

import nl.han.dea.datasource.impl.SubscriptionDaoImpl;
import nl.han.dea.domain.Subscription;
import nl.han.dea.store.SubscriptionMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;


public class SubscriptionDaoImplTest {

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @InjectMocks
    private SubscriptionDao subscriptionDao;

    @Before
    public void init() {
        subscriptionDao = new SubscriptionDaoImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTheRightSubscriptionFromIdentityMap() throws SQLException {
        // init
        Subscription subscription = new Subscription();
        subscription.setId(1);
        Mockito.when(subscriptionMapper.find(1)).thenReturn(subscription);

        // run
        Subscription subscription1 = subscriptionDao.getSubscription(1);

        // assert
        Assert.assertEquals(1, subscription1.getId());
    }

    @Test(expected=NullPointerException.class)
    public void getSubscriptionFromIdentityMapThatNotExists() throws SQLException {
        // init
        Subscription subscription = new Subscription();
        subscription.setId(1);
        Mockito.when(subscriptionMapper.find(1)).thenReturn(subscription);

        // assert
        Assert.assertEquals(2, subscriptionDao.getSubscription(2).getId());
    }
}
