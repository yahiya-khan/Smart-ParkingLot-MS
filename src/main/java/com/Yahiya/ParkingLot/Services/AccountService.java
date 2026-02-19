package main.java.com.Yahiya.ParkingLot.Services;

import main.java.com.Yahiya.ParkingLot.Models.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private final Map<String, User> userRegistry = new HashMap<>();

    public boolean hasAdmin() {
        return userRegistry.values().stream()
                .anyMatch(u -> u.getRole() == User.UserRole.OWNER);
    }

    /**
     * Unified registration method used for both initial setup and staff management.
     */
    public void registerUser(String username, String password, String name, User.UserRole role) {
        if (userRegistry.containsKey(username)) {
            System.out.println("⚠️ Error: Username '" + username + "' already exists.");
            return;
        }
        // Fixed: Passing 4 arguments to match the updated User model
        User newUser = new User(username, password, name, role);
        userRegistry.put(username, newUser);
    }

    public User authenticate(String username, String password) {
        User user = userRegistry.get(username);
        if (user == null) return null;

        String hashedAttempt = hashPassword(password);
        return user.getPassword().equals(hashedAttempt) ? user : null;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return password; 
        }
    }
}