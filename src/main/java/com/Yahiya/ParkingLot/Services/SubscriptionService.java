package main.java.com.Yahiya.ParkingLot.Services;

import main.java.com.Yahiya.ParkingLot.Models.Subscription;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionService {
    private static final Map<String, Subscription> subscriptions = new HashMap<>();

    public static void addSubscription(Subscription sub) {
        subscriptions.put(sub.getLicensePlate(), sub);
    }

    /**
     * This is the missing method! 
     * It allows the Fee Strategy to get the specific subscription details.
     */
    public static Subscription getSubscription(String licensePlate) {
        return subscriptions.get(licensePlate);
    }

    public static boolean isSubscribed(String licensePlate) {
        Subscription sub = subscriptions.get(licensePlate);
        return sub != null && sub.isValid();
    }
}