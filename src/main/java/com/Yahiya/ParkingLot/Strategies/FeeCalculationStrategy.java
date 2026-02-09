package main.java.com.Yahiya.ParkingLot.Strategies;

import main.java.com.Yahiya.ParkingLot.enums.VehicleType;


public interface FeeCalculationStrategy {
    double calculateFee(VehicleType vehicleType, long hours);
}
