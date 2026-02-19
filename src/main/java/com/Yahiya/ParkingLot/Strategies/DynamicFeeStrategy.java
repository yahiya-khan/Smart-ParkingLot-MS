package main.java.com.Yahiya.ParkingLot.Strategies;

import main.java.com.Yahiya.ParkingLot.Models.ParkingTicket;
import main.java.com.Yahiya.ParkingLot.Models.PricingConfig;
import main.java.com.Yahiya.ParkingLot.Services.SubscriptionService;
import main.java.com.Yahiya.ParkingLot.Models.Subscription;

public class DynamicFeeStrategy {
    private PricingConfig pricingConfig;
    private static final double VIP_SURCHARGE = 1.5; 
    public DynamicFeeStrategy(PricingConfig pricingConfig) {
        this.pricingConfig = pricingConfig;
    }

   public double calculateFee(ParkingTicket ticket) {
    String plate = ticket.getVehicle().getLicensePlate();
    long hoursStayed = calculateHours(ticket.getEntryTime());

    // 1. Check for Active Subscription
    if (SubscriptionService.isSubscribed(plate)) {
        Subscription sub = SubscriptionService.getSubscription(plate);
        int limit = sub.getDailyHourLimit();

        if (hoursStayed <= limit) {
            return 0.0; // Within subscription limits
        } else {
            // Charge only for the extra hours (Overstay)
            long extraHours = hoursStayed - limit;
            double rate = pricingConfig.getRate(ticket.getVehicle().getType().name());
            System.out.println("⚠️ Subscription limit exceeded by " + extraHours + " hrs.");
            return extraHours * rate;
        }
    }

    // 2. Standard Calculation for non-subscribers
    double rate = pricingConfig.getRate(ticket.getVehicle().getType().name());
    return hoursStayed * rate;
}

private long calculateHours(java.time.LocalDateTime entry) {
    java.time.Duration d = java.time.Duration.between(entry, java.time.LocalDateTime.now());
    long h = d.toHours();
    return (d.toMinutes() % 60 > 0 || h == 0) ? h + 1 : h;
}
}