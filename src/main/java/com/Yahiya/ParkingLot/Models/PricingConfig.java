package main.java.com.Yahiya.ParkingLot.Models;

import main.java.com.Yahiya.ParkingLot.enums.VehicleType;
import java.util.HashMap;
import java.util.Map;

public class PricingConfig {
    private Map<String, Double> rates = new HashMap<>();

    public PricingConfig() {
        // --- DEFAULTS ---
        // We set these up immediately so the owner doesn't have to.
        rates.put(VehicleType.Bike.name().toUpperCase(), 10.0);
        rates.put(VehicleType.ThreeWheel.name().toUpperCase(), 15.0);
        rates.put(VehicleType.Car.name().toUpperCase(), 25.0);
        rates.put(VehicleType.Bus.name().toUpperCase(), 60.0);
    }

    // Owner can override a standard rate or add a new "Purpose"
    public void setRate(String category, Double rate) {
        rates.put(category.toUpperCase(), rate);
    }

    public Double getRate(String category) {
        // If the owner searches for a category that doesn't exist, 
        // we return 20.0 as a safe "fallback" price.
        return rates.getOrDefault(category.toUpperCase(), 20.0);
    }
}