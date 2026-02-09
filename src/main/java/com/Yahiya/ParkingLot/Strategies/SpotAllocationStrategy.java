package main.java.com.Yahiya.ParkingLot.Strategies;

import main.java.com.Yahiya.ParkingLot.Models.ParkingFloor;
import main.java.com.Yahiya.ParkingLot.Models.ParkingSpot;
import main.java.com.Yahiya.ParkingLot.enums.VehicleType;
import java.util.List;

public interface SpotAllocationStrategy {
    ParkingSpot allocateSpot(VehicleType vehicleType, List<ParkingFloor> floors);
}

