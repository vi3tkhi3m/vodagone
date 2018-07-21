package nl.han.dea.dtos.subscription;

import nl.han.dea.domain.Subscription;

import java.util.ArrayList;

public class SubscriptionResponse {


    private int totalPrice = 0;
    private ArrayList<Subscription> abonnementen = new ArrayList<>();

    public void addSubscriptionToList(Subscription subscription) {
        this.abonnementen.add(subscription);
    }

    public ArrayList<Subscription> getAbonnementen() {
        return abonnementen;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addToTotalPrice(int price) {
        totalPrice += price;
    }
}
