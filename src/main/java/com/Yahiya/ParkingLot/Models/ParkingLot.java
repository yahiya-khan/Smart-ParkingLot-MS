package main.java.com.Yahiya.ParkingLot.Models;

import java.util.List;
import java.util.Map;

public class ParkingLot {

    private String parkingLotId;
    private String parkingLotName;
    private List<ParkingFloor> floors;
    private Map<String, ParkingTicket> activeTickets;

    public ParkingLot(String parkingLotId,
                      String parkingLotName,
                      List<ParkingFloor> floors,
                      Map<String, ParkingTicket> activeTickets) {
        this.parkingLotId = parkingLotId;
        this.parkingLotName = parkingLotName;
        this.floors = floors;
        this.activeTickets = activeTickets;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public List<ParkingFloor> getFloors() {
        return floors;
    }

    public Map<String, ParkingTicket> getActiveTickets() {
        return activeTickets;
    }
}
