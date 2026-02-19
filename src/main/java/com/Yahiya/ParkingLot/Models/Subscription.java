package main.java.com.Yahiya.ParkingLot.Models;

import java.time.LocalDate;

public class Subscription {
    private String licensePlate;
    private LocalDate expiryDate;
    private int dailyHourLimit; // e.g., 9 hours
    private boolean isActive;

    public Subscription(String licensePlate, LocalDate expiryDate, int dailyHourLimit) {
        this.licensePlate = licensePlate;
        this.expiryDate = expiryDate;
        this.dailyHourLimit = dailyHourLimit;
        this.isActive = true;
    }

    public boolean isValid() {
        return isActive && !LocalDate.now().isAfter(expiryDate);
    }

    public int getDailyHourLimit() { return dailyHourLimit; }
    public String getLicensePlate() { return licensePlate; }
}