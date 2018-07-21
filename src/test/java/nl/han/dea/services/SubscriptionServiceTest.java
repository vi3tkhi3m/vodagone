package nl.han.dea.services;

import nl.han.dea.datasource.LoginDao;
import nl.han.dea.datasource.SubscriberSubscriptionDao;
import nl.han.dea.datasource.SubscriptionDao;
import nl.han.dea.datasource.UserSubscriptionsDao;
import nl.han.dea.dtos.subscription.SubscriptionDetailsResponse;
import nl.han.dea.services.subscription.SubscriptionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.SQLException;

public class SubscriptionServiceTest {

    @Mock
    UserSubscriptionsDao userSubscriptionsDao;

    @Mock
    SubscriptionDao subscriptionDao;

    @Mock
    SubscriberSubscriptionDao subscriberSubscriptionDao;

    @Mock
    LoginDao loginDao;

    @Spy
    @InjectMocks
    SubscriptionService subscriptionService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void showSuccesWhenTerminatingSubscription() throws SQLException {
        // init
        SubscriptionDetailsResponse subscriptionDetailsResponse = new SubscriptionDetailsResponse();
        Mockito.when(userSubscriptionsDao.terminateUserSubscription(1, 1)).thenReturn(true);

        // assert
        Assert.assertEquals(0, subscriptionService.terminateSubscriptionFromUser("1",1).getId());
    }
}
