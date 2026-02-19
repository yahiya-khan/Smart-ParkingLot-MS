package main.java.com.Yahiya.ParkingLot.Services;

import main.java.com.Yahiya.ParkingLot.Models.*;
import main.java.com.Yahiya.ParkingLot.Strategies.SpotAllocationStrategy;
import java.util.Optional;

public class ParkingService {
    private SpotAllocationStrategy allocationStrategy;

    public ParkingService(SpotAllocationStrategy strategy) {
        this.allocationStrategy = strategy;
    }

    /**
     * ACTION 1: ENTRY
     * Handles spot allocation and ticket generation when a vehicle arrives.
     */
    public ParkingTicket handleEntry(Vehicle vehicle, ParkingLot lot) throws Exception {
        // 1. Uses the strategy to find an empty spot in the lot's floors
        Optional<ParkingSpot> spotOptional = allocationStrategy.allocateSpot(vehicle.getType(), lot.getFloors());

        if (spotOptional.isEmpty()) {
            throw new Exception("Parking Lot Full for vehicle type: " + vehicle.getType());
        }

        ParkingSpot spot = spotOptional.get();

        // 2. Mark the spot as occupied 
        spot.setFree(false);

        // 3. Create a Ticket
        ParkingTicket ticket = new ParkingTicket(vehicle, spot, "GATE-01");

        // 4. Save the ticket into the lot's map 
        lot.getActiveTickets().put(ticket.getTicketNumber(), ticket);

        System.out.println("Entry successful! Ticket issued: " + ticket.getTicketNumber());
        return ticket;
    }

    /**
     * ACTION 2: EXIT
     * Frees the spot and removes the ticket from the active system.
     */
    public void handleExit(String ticketNumber, ParkingLot lot) throws Exception {
        // 1. Find the ticket in the map
        ParkingTicket ticket = lot.getActiveTickets().get(ticketNumber);

        if (ticket == null) {
            throw new Exception("Invalid Ticket Number!");
        }

        // 2. Free the spot
        ticket.getParkingSpot().setFree(true);

        // 3. Remove from active tickets
        lot.getActiveTickets().remove(ticketNumber);

        System.out.println("Exit successful for: " + ticketNumber);
    }
}