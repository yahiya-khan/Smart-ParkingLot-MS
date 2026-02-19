package main.java.com.Yahiya.ParkingLot.Models;

import java.util.List;

public class ParkingFloor {

    private int floorNumber;
    private List<ParkingSpot> parkingSpots;

    public ParkingFloor(int floorNumber, List<ParkingSpot> parkingSpots) {
        this.floorNumber = floorNumber;
        this.parkingSpots = parkingSpots;
    }

    public int getFloorNumber() {
        return floorNumber;
    }


    public List<ParkingSpot> getParkingSpots() {
    return this.parkingSpots;
}

}
