package store.impl;

import domain.UserSubscriptions;
import exceptions.DataMapperException;
import store.UserSubscriptionMapper;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

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
    public List<UserSubscriptions> find(int user_id) {

        List<UserSubscriptions> userSubscription = new ArrayList<>();

        System.out.println("List size of USMAPPER: " + userSubscriptions.size());

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
