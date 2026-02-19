package main.java.com.Yahiya.ParkingLot.Models;

import java.time.LocalDateTime;
import java.util.UUID;

public class ParkingTicket {
    private String ticketNumber;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private LocalDateTime entryTime;
    private String entryGate;

    public ParkingTicket(Vehicle vehicle, ParkingSpot parkingSpot, String entryGate) {
        this.ticketNumber = "TIC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryGate = entryGate;
        this.entryTime = LocalDateTime.now();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public String getEntryGate() {
        return entryGate;
    }
}