package domain;

public class SubscriberSubscription {

    private int id;
    private int subscriber_id;
    private int userSubscription_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(int subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public int getUserSubscription_id() {
        return userSubscription_id;
    }

    public void setUserSubscription_id(int userSubscription_id) {
        this.userSubscription_id = userSubscription_id;
    }
}
