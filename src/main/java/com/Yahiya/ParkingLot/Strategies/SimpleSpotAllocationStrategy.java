package main.java.com.Yahiya.ParkingLot.Strategies;

import main.java.com.Yahiya.ParkingLot.Models.*;
import main.java.com.Yahiya.ParkingLot.enums.VehicleType;
import java.util.List;
import java.util.Optional;

/**
 * A simple strategy that iterates through floors and spots 
 * and returns the first available spot that matches the vehicle type.
 */
public class SimpleSpotAllocationStrategy implements SpotAllocationStrategy {
    
    @Override
    public Optional<ParkingSpot> allocateSpot(VehicleType vehicleType, List<ParkingFloor> floors) {
        if (floors == null || vehicleType == null) {
            return Optional.empty();
        }

        for (ParkingFloor floor : floors) {
            // Iterate through each spot on the current floor
            for (ParkingSpot spot : floor.getParkingSpots()) { 
                
                // 1. Check if the spot is physically free
                // 2. Check if the spot supports the specific VehicleType
                if (spot.isFree() && isTypeMatch(spot, vehicleType)) {
                    return Optional.of(spot);
                }
            }
        }
        
        // No matching free spot found in the entire lot
        return Optional.empty();
    }

    /**
     * Helper to safely check type matching and avoid NullPointerExceptions
     */
    private boolean isTypeMatch(ParkingSpot spot, VehicleType vehicleType) {
        return spot.getSupportedType() != null && spot.getSupportedType() == vehicleType;
    }
}