package main.java.com.Yahiya.ParkingLot.Models;

import main.java.com.Yahiya.ParkingLot.enums.VehicleType;

public class ParkingSpot {
    private String id;
    private VehicleType supportedType;
    private boolean isFree;
    private boolean isReserved; // NEW
    private String reservedForPlate; // NEW

    public ParkingSpot(String id, VehicleType supportedType) {
        this.id = id;
        this.supportedType = supportedType;
        this.isFree = true;
        this.isReserved = false;
    }

    // Logic to reserve a spot
    public void reserve(String licensePlate) {
        this.isReserved = true;
        this.reservedForPlate = licensePlate;
        this.isFree = false; // Reserved spots are not "Free" for others
    }

    public void releaseReservation() {
        this.isReserved = false;
        this.reservedForPlate = null;
        this.isFree = true;
    }

    // Getters and Setters
    public String getId() { return id; }
    public VehicleType getSupportedType() { return supportedType; }
    public boolean isFree() { return isFree; }
    public void setFree(boolean free) { isFree = free; }
    public boolean isReserved() { return isReserved; }
    public String getReservedForPlate() { return reservedForPlate; }
}