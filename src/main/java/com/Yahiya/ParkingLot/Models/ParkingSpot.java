package main.java.com.Yahiya.ParkingLot.Models;


import main.java.com.Yahiya.ParkingLot.enums.VehicleType;

public class ParkingSpot {

    private String spotId;
    private VehicleType supportedType;
    private boolean available;

    public ParkingSpot(String spotId, VehicleType supportedType) {
        this.spotId = spotId;
        this.supportedType = supportedType;
        this.available = true;
    }

    public String getSpotId() {
        return spotId;
    }

    public VehicleType getSupportedType() {
        return supportedType;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
