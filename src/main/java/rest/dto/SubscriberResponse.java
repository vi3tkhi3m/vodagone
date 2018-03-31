package rest.dto;

import domain.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class SubscriberResponse {
    private List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriberToList(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }
}
