package main.java.com.Yahiya.ParkingLot.Models;

import main.java.com.Yahiya.ParkingLot.enums.VehicleType;

/**
 * The Base Abstract Class
 */
public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }
}

/**
 * Concrete Subclasses (Package-Private)
 */
class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.Car);
    }
}

class Bike extends Vehicle {
    public Bike(String licensePlate) {
        super(licensePlate, VehicleType.Bike);
    }
}

class Bus extends Vehicle {
    public Bus(String licensePlate) {
        super(licensePlate, VehicleType.Bus);
    }
}

class ThreeWheel extends Vehicle {
    public ThreeWheel(String licensePlate) {
        super(licensePlate, VehicleType.ThreeWheel);
    }
}