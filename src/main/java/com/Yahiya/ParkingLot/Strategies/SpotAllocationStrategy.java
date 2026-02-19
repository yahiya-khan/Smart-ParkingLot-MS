package main.java.com.Yahiya.ParkingLot.Strategies;

import main.java.com.Yahiya.ParkingLot.Models.ParkingFloor;
import main.java.com.Yahiya.ParkingLot.Models.ParkingSpot;
import main.java.com.Yahiya.ParkingLot.enums.VehicleType;
import java.util.List;
import java.util.Optional;

public interface SpotAllocationStrategy {
    Optional<ParkingSpot> allocateSpot(VehicleType vehicleType, List<ParkingFloor> floors);
}

