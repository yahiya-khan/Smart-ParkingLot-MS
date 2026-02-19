package main.java.com.Yahiya.ParkingLot;

import main.java.com.Yahiya.ParkingLot.Models.*;
import main.java.com.Yahiya.ParkingLot.enums.VehicleType;
import main.java.com.Yahiya.ParkingLot.Services.*;
import main.java.com.Yahiya.ParkingLot.Strategies.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final AccountService accountService = new AccountService();
    private static final PricingConfig pricing = new PricingConfig();
    private static ParkingLot myLot = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!accountService.hasAdmin()) {
            initializeSystem(scanner);
        }

        User currentUser = login(scanner);

        if (currentUser != null) {
            runMenu(scanner, currentUser);
        }
        
        System.out.println("System Shutting Down...");
        scanner.close();
    }

    private static void initializeSystem(Scanner scanner) {
        System.out.println("Welcome! Initializing Smart Parking System...");
        System.out.print("Create Owner Username: ");
        String adminU = scanner.nextLine();
        System.out.print("Create Owner Password: ");
        String adminP = scanner.nextLine();

        // Explicitly passing 'OWNER' role
        accountService.registerUser(adminU, adminP, "System Owner", User.UserRole.OWNER);
        myLot = setupParkingLot(scanner);
    }

    private static User login(Scanner scanner) {
        User user = null;
        while (user == null) {
            System.out.println("\n LOGIN REQUIRED");
            System.out.print("Username: ");
            String u = scanner.nextLine();
            System.out.print("Password: ");
            String p = scanner.nextLine();
            user = accountService.authenticate(u, p);
            if (user == null) System.out.println(" Invalid input. Access Denied!");
        }
        return user;
    }

    private static ParkingLot setupParkingLot(Scanner sc) {
        System.out.print("\nEnter Parking Lot Name: ");
        String name = sc.nextLine();
        System.out.print("How many floors? ");
        int floorCount = sc.nextInt();

        List<ParkingFloor> floors = new ArrayList<>();
        for (int i = 1; i <= floorCount; i++) {
            List<ParkingSpot> spots = new ArrayList<>();
            System.out.println("\n--- Configuring Floor " + i + " ---");
            
            for (VehicleType type : VehicleType.values()) {
                System.out.print("Number of spots for " + type + ": ");
                int count = sc.nextInt();
                addSpots(spots, i, count, type.name());
            }

            System.out.print("Add Custom Vehicle Type? (1=Yes, 0=No): ");
            if (sc.nextInt() == 1) {
                sc.nextLine(); 
                System.out.print("Enter Custom Vehicle Name: ");
                String customName = sc.nextLine();
                System.out.print("Enter Hourly Fare: ");
                double fare = sc.nextDouble();
                System.out.print("Enter Number of spots: ");
                int customSpots = sc.nextInt();

                pricing.setRate(customName, fare);
                addSpots(spots, i, customSpots, customName);
            }
            floors.add(new ParkingFloor(i, spots));
        }
        sc.nextLine(); 
        return new ParkingLot("PL-01", name, floors, new HashMap<>());
    }

    private static void addSpots(List<ParkingSpot> list, int floor, int count, String typeName) {
        int currentSize = list.size();
        VehicleType vType = null;
        for (VehicleType val : VehicleType.values()) {
            if (val.name().equalsIgnoreCase(typeName)) {
                vType = val;
                break;
            }
        }
        for (int i = 1; i <= count; i++) {
            list.add(new ParkingSpot("F" + floor + "-" + typeName.toUpperCase() + "-" + (currentSize + i), vType)); 
        }
    }

   private static void runMenu(Scanner sc, User user) {
        ParkingService parkingService = new ParkingService(new SimpleSpotAllocationStrategy());
        DynamicFeeStrategy feeStrategy = new DynamicFeeStrategy(pricing);

        while (true) {
            System.out.println("\n" + "=".repeat(30));
            System.out.println("DASHBOARD: " + user.getName().toUpperCase());
            System.out.println("Role: " + user.getRole() + " | Lot: " + myLot.getName());
            System.out.println("=".repeat(30));
            System.out.println("1. Entry (Park Vehicle)");
            System.out.println("2. Exit (Process Bill)");
            System.out.println("3. Issue Subscription Pass");
            System.out.println("4. View Occupancy Map");
            System.out.println("5. View Active Tickets");
            
           if (user.getRole() == User.UserRole.OWNER || user.getRole() == User.UserRole.MANAGER) {
            System.out.println("6. Register Staff/Manage Users");
        }
        
        // Only Owner can manage rates
        if (user.getRole() == User.UserRole.OWNER) {
            System.out.println("7. Manage System Rates");
        }
            System.out.println("0. Logout");
            System.out.print("\nSelect Action: ");
            
            String choiceStr = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1 : processEntry(sc, parkingService);
                    break;
                    case 2 : processExit(sc, parkingService, feeStrategy);
                    break;
                    case 3 : issueSubscription(sc);
                    break;
                    case 4 : viewOccupancy(myLot);
                    break;
                    case 5 : listActiveTickets(myLot);
                    break;
                    case 6 : {
                        if (user.getRole() == User.UserRole.OWNER || user.getRole() == User.UserRole.MANAGER) registerStaff(sc, user);
                        else System.out.println("Access Denied.");
                    }
                    break;
                    case 7 : {
                        if (user.getRole() == User.UserRole.OWNER) manageRates(sc);
                        else System.out.println("Access Denied.");
                    }
                    break;
                    case 0 : {
                        System.out.println("Logging out...");
                        return;
                    }
                    default : System.out.println("Invalid Option.");
                    break;
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
            }
        }
    }

    // LOGIC FOR EACH ACTION BELOW

  private static void processEntry(Scanner sc, ParkingService service) throws Exception {
        System.out.println("\n--- VEHICLE ENTRY ---");
        System.out.print("Enter Plate Number: ");
        String plate = sc.nextLine();
        System.out.print("Select Vehicle Type: ");
        String typeInput = sc.nextLine();
        
        Vehicle vehicle = VehicleFactory.createVehicle(typeInput, plate);
        ParkingTicket ticket = service.handleEntry(vehicle, myLot);
        
        System.out.println("\n ENTRY GRANTED!");
        System.out.println("Ticket ID: " + ticket.getTicketNumber() + " | Spot: " + ticket.getParkingSpot().getId());
        // returns to loop
    }

  private static void processExit(Scanner sc, ParkingService service, DynamicFeeStrategy fees) throws Exception {
    System.out.println("\n---  VEHICLE EXIT & BILLING ---");
    
    // 1. Get all active tickets
    Map<String, ParkingTicket> ticketsMap = myLot.getActiveTickets();
    if (ticketsMap.isEmpty()) {
        System.out.println(" No vehicles are currently parked in the lot.");
        return;
    }

    // 2. Display them in a numbered list
    List<String> ticketIds = new ArrayList<>(ticketsMap.keySet());
    System.out.println("Select a vehicle to exit:");
    for (int i = 0; i < ticketIds.size(); i++) {
        ParkingTicket t = ticketsMap.get(ticketIds.get(i));
        System.out.println((i + 1) + ". Plate: " + t.getVehicle().getLicensePlate() + " | Spot: " + t.getParkingSpot().getId());
    }
    System.out.println("0. Back to Dashboard");
    
    // 3. Let user pick the index
    System.out.print("\nChoice: ");
    int selection = Integer.parseInt(sc.nextLine());

    if (selection == 0) return;
    if (selection < 1 || selection > ticketIds.size()) {
        System.out.println(" Invalid selection.");
        return;
    }

    // 4. Retrieve the actual ticket
    String selectedTicketId = ticketIds.get(selection - 1);
    ParkingTicket ticket = ticketsMap.get(selectedTicketId);

    // 5. Calculate Fee
    double amount = fees.calculateFee(ticket);
    System.out.println("\n" + "-".repeat(20));
    System.out.println("RECEIPT FOR: " + ticket.getVehicle().getLicensePlate());
    System.out.println("TOTAL FEE: ₹" + amount);
    System.out.println("-".repeat(20));

    if (amount > 0) {
        System.out.print("Confirm Payment? (1 for YES): ");
        if (!sc.nextLine().equals("1")) {
            System.out.println(" Payment not confirmed. Exit cancelled.");
            return;
        }
    }

    // 6. Finalize Exit
    service.handleExit(selectedTicketId, myLot);
    System.out.println(" Exit Successful. Have a nice day!");
}

    private static void issueSubscription(Scanner sc) {
        System.out.println("\n--- ISSUE SUBSCRIPTION ---");
        System.out.print("Enter Plate: ");
        String plate = sc.nextLine();
        System.out.print("Daily Hour Limit: ");
        int limit = Integer.parseInt(sc.nextLine());
        System.out.print("Validity (Days): ");
        int days = Integer.parseInt(sc.nextLine());

        Subscription sub = new Subscription(plate, LocalDate.now().plusDays(days), limit);
        SubscriptionService.addSubscription(sub);
        System.out.println(" Subscription active until " + LocalDate.now().plusDays(days));
        System.out.println("(Press Enter to return)");
        sc.nextLine();
    }

    private static void registerStaff(Scanner sc, User currentUser) {
        System.out.println("\n--- USER REGISTRATION ---");
        System.out.print("Enter New Username: ");
        String u = sc.nextLine();
        System.out.print("Enter Password: ");
        String p = sc.nextLine();
        System.out.print("Enter Full Name: ");
        String name = sc.nextLine();

        System.out.println("Select Role:");
        // Only Owners can create Managers
        if (currentUser.getRole() == User.UserRole.OWNER) {
            System.out.println("1. MANAGER");
        }
        System.out.println("2. OPERATOR");

        String roleChoice = sc.nextLine();
        User.UserRole selectedRole = User.UserRole.OPERATOR;

        if (roleChoice.equals("1") && currentUser.getRole() == User.UserRole.OWNER) {
            selectedRole = User.UserRole.MANAGER;
        }

        accountService.registerUser(u, p, name, selectedRole);
        System.out.println(" " + selectedRole + " added successfully.");
    }

private static void viewOccupancy(ParkingLot lot) {
    System.out.println("\n---  LIVE OCCUPANCY MAP ---");
    for (ParkingFloor floor : lot.getFloors()) {
        System.out.println("Floor " + floor.getFloorNumber() + ":");
        for (ParkingSpot spot : floor.getParkingSpots()) {
            String status = spot.isFree() ? "[  FREE  ]" : "[ OCCUPIED ]";
            System.out.printf("  Spot %-15s: %s (%s)\n", spot.getId(), status, spot.getSupportedType());
        }
    }
}

private static void listActiveTickets(ParkingLot lot) {
    System.out.println("\n---  ACTIVE TICKETS ---");
    Map<String, ParkingTicket> tickets = lot.getActiveTickets();
    if (tickets.isEmpty()) {
        System.out.println("No vehicles currently parked.");
        return;
    }
    int i = 1;
    for (String id : tickets.keySet()) {
        ParkingTicket t = tickets.get(id);
        System.out.println(i + ". ID: " + id + " | Plate: " + t.getVehicle().getLicensePlate() + " | Type: " + t.getVehicle().getType());
        i++;
    }
}

private static void manageRates(Scanner sc) {
        System.out.println("\n--- MANAGE SYSTEM RATES ---");
        System.out.print("Enter Vehicle Type to update (e.g., CAR, BIKE): ");
        String type = sc.nextLine().toUpperCase();
        System.out.print("Enter New Hourly Rate: ");
        double newRate = Double.parseDouble(sc.nextLine());
        
        pricing.setRate(type, newRate);
        System.out.println("Rate updated successfully for " + type);
    }

}