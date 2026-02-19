package main.java.com.Yahiya.ParkingLot.Models; // Same package as Vehicle

import main.java.com.Yahiya.ParkingLot.enums.VehicleType;

public class VehicleFactory {

    public static Vehicle createVehicle(String typeInput, String licensePlate) throws Exception {
        VehicleType selectedType = null;

        for (VehicleType vType : VehicleType.values()) {
            if (vType.name().equalsIgnoreCase(typeInput)) {
                selectedType = vType;
                break;
            }
        }

        if (selectedType == null) {
            throw new Exception("Vehicle type '" + typeInput + "' is not supported.");
        }

        // Because this is in the same package, it can see Car, Bike, etc.
        switch (selectedType) {
            case Car: return new Car(licensePlate);
            case Bike: return new Bike(licensePlate);
            case Bus: return new Bus(licensePlate);
            case ThreeWheel: return new ThreeWheel(licensePlate);
            default: throw new Exception("Implementation missing.");
        }
    }
}