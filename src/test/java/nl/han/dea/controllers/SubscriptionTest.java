package nl.han.dea.controllers;

import nl.han.dea.controllers.subscription.Subscription;
import nl.han.dea.dtos.subscription.SubscriptionRequest;
import nl.han.dea.dtos.subscription.SubscriptionResponse;
import nl.han.dea.services.subscription.SubscriptionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

public class SubscriptionTest {

    @Mock
    SubscriptionService subscriptionService;

    @InjectMocks
    Subscription subscription;

    @Before
    public void init() {
        subscription = new Subscription();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getSuccesWhenAddingSubscription() throws SQLException {
        // init
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
        Mockito.when(subscriptionService.addSubscriptionToUser("", 1)).thenReturn(subscriptionResponse);

        // assert
        Assert.assertEquals(200, subscription.addSubscription("",subscriptionRequest).getStatus());
    }

}
