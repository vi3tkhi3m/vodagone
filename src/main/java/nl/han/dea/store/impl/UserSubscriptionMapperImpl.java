package nl.han.dea.store.impl;

import nl.han.dea.domain.UserSubscriptions;
import nl.han.dea.exceptions.DataMapperException;
import nl.han.dea.store.UserSubscriptionMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.*;

@Default
@ApplicationScoped
public class UserSubscriptionMapperImpl implements UserSubscriptionMapper {

    private ArrayList<UserSubscriptions> userSubscriptions = new ArrayList<>();

    @Override
    public UserSubscriptions find(int subscription_id, int user_id) {

        for (final UserSubscriptions us : this.getUserSubscriptions()) {
            if (us.getUser_id() == user_id && us.getSubscription_id() == subscription_id) {
                return us;
            }
        }
        return null;
    }

    @Override
    public UserSubscriptions findUserSubscriptionByID(int userSubscription_id) {
        for (final UserSubscriptions us : this.getUserSubscriptions()) {
            if (us.getId() == userSubscription_id) {
                return us;
            }
        }
        return null;
    }

    @Override
    public List<UserSubscriptions> find(int user_id) {

        List<UserSubscriptions> userSubscription = new ArrayList<>();

        for(UserSubscriptions us: userSubscriptions) {
            if(us.getUser_id() == user_id) {
                userSubscription.add(us);
            }
        }

        return userSubscription;
    }

    @Override
    public void insert(UserSubscriptions userSubscriptionToBeInserted) throws DataMapperException {
        if (!this.getUserSubscriptions().contains(userSubscriptionToBeInserted)) {
            this.getUserSubscriptions().add(userSubscriptionToBeInserted);

        } else {
            throw new DataMapperException("Subscription [" + userSubscriptionToBeInserted.getId() + "] already exists");
        }
    }

    @Override
    public void update(UserSubscriptions userSubscriptionToBeUpdated) throws DataMapperException {
        if (this.getUserSubscriptions().contains(userSubscriptionToBeUpdated)) {
            final int index = this.getUserSubscriptions().indexOf(userSubscriptionToBeUpdated);
            this.getUserSubscriptions().set(index, userSubscriptionToBeUpdated);
            System.out.print("Usersubscription is geupdate!");
        } else {
            throw new DataMapperException("Subscription [" + userSubscriptionToBeUpdated.getId() + "] is not found");
        }
    }

    @Override
    public void delete(UserSubscriptions userSubscriptionToBeDeleted) throws DataMapperException {
        if (this.getUserSubscriptions().contains(userSubscriptionToBeDeleted)) {
            this.getUserSubscriptions().remove(userSubscriptionToBeDeleted);

        } else {
            throw new DataMapperException("Subscription [" + userSubscriptionToBeDeleted.getId() + "] is not found");
        }
    }

    @Override
    public List<UserSubscriptions> getUserSubscriptions() {
        return userSubscriptions;
    }
}
