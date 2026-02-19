package main.java.com.Yahiya.ParkingLot.Strategies;

import main.java.com.Yahiya.ParkingLot.Models.ParkingTicket;

public interface FeeCalculationStrategy {
    // passes the whole ticket so the strategy can see entryTime, vehicleType, etc.
    double calculateFee(ParkingTicket ticket);
}