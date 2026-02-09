package main.java.com.Yahiya.ParkingLot;

import main.java.com.Yahiya.ParkingLot.Models.*;
import main.java.com.Yahiya.ParkingLot.enums.VehicleType;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("🚗 Smart Parking Lot System Started...");

        // Create empty lists
        List<ParkingSpot> spots = new ArrayList<>();
        List<ParkingFloor> floors = new ArrayList<>();
        Map<String, ParkingTicket> activeTickets = new HashMap<>();

        // Create floor
        ParkingFloor floor1 = new ParkingFloor(1, spots);
        floors.add(floor1);

        // Create parking lot
        ParkingLot parkingLot = new ParkingLot(
                "PL001",
                "City Center Parking",
                floors,
                activeTickets
        );

        System.out.println("Parking Lot Created: " + parkingLot.getParkingLotName());
        System.out.println("Total Floors: " + parkingLot.getFloors().size());

        System.out.println("System initialized successfully ✅");
    }
}