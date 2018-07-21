package nl.han.dea.store;

import nl.han.dea.domain.Subscription;
import nl.han.dea.exceptions.DataMapperException;
import nl.han.dea.store.impl.SubscriptionMapperImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SubscriptionMapperTest {

    SubscriptionMapper subscriptionMapper;
    Subscription subscription;

    @Before
    public void init() {
        subscriptionMapper = new SubscriptionMapperImpl();
        subscription = new Subscription();
    }

    @Test
    public void testGetEmptyList() {
        // run
        int count = subscriptionMapper.getSubscriptions().size();

        // assert
        Assert.assertEquals(0, count);
    }

    @Test
    public void testInsert() {
        // init
        subscriptionMapper.insert(subscription);

        // run
        int count = subscriptionMapper.getSubscriptions().size();

        // assert
        Assert.assertEquals(1, count);
    }

    @Test
    public void testMultipleInserts() {
        // init
        for(int i = 0; i < 5; i++) {
            Subscription subscription = new Subscription();
            subscription.setId(i);
            subscriptionMapper.insert(subscription);
        }

        // run
        int count = subscriptionMapper.getSubscriptions().size();

        // assert
        Assert.assertEquals(5, count);
    }

    @Test
    public void updateExistingSubscriptionInList() {
        // init
        subscription.setId(1);
        subscriptionMapper.insert(subscription);

        // run
        subscription.setAanbieder("test");
        subscriptionMapper.update(subscription);

        // assert
        Assert.assertEquals("test", subscriptionMapper.find(1).getAanbieder().toString().toLowerCase());
    }

    @Test(expected = DataMapperException.class)
    public void updateSomethingThatNotExists() {
        subscriptionMapper.update(subscription);
    }

}
