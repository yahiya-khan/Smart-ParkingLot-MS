package main.java.com.Yahiya.ParkingLot.Models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class User {
    private String id;
    private String username;
    private String password; 
    private UserRole role;
    private String name;

    // Added MANAGER role
    public enum UserRole { OWNER, MANAGER, OPERATOR }

    public User(String username, String password, String name, UserRole role) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = hashPassword(password);
        this.role = role; 
        this.name = name;
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

    public String getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}