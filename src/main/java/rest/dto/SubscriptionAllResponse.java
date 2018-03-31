package rest.dto;

import domain.Subscription;

import java.util.ArrayList;

public class SubscriptionAllResponse {
    private ArrayList<Subscription> subscription = new ArrayList<>();

    public void addSubscriptionToList(Subscription subscription) {
        this.subscription.add(subscription);
    }

    public ArrayList<Subscription> getAbonnement(String filter) {
        if("".equals(filter)) {
            return subscription;
        }
        ArrayList<Subscription> filteredSubscriptionArray = new ArrayList<>();
        for(Subscription a: subscription) {
            if(a.getAanbieder().toLowerCase().matches("(.*)"+ filter.toLowerCase() +"(.*)")) {
                filteredSubscriptionArray.add(a);
            }else if(a.getDienst().toLowerCase().matches("(.*)"+ filter.toLowerCase() +"(.*)")) {
                filteredSubscriptionArray.add(a);
            }
        }
        return filteredSubscriptionArray;
    }
}
