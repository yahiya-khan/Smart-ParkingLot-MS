package main.java.com.Yahiya.ParkingLot.Util;

import main.java.com.Yahiya.ParkingLot.Models.*;
import main.java.com.Yahiya.ParkingLot.enums.VehicleType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingLotFactory {
    
    public static ParkingLot createCustomLot(String id, String name, int numFloors, int spotsPerFloor) {
        List<ParkingFloor> floors = new ArrayList<>();
        
        for (int i = 1; i <= numFloors; i++) {
            List<ParkingSpot> spots = new ArrayList<>();
            for (int j = 1; j <= spotsPerFloor; j++) {
                // Industry Logic: Assign types to spots (e.g., spots 1-2 are bus, 3-10 are Car)
                VehicleType type = (j <= 2) ? VehicleType.Bus : VehicleType.Car;
                spots.add(new ParkingSpot("F" + i + "-S" + j, type));
            }
            floors.add(new ParkingFloor(i, spots));
        }
        return new ParkingLot(id, name, floors, new HashMap<>());
    }
}