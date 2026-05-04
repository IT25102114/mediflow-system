package com.mediflow.service;

import com.mediflow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User Service — Handles all user-related CRUD operations using file I/O.
 * Demonstrates OOP by working with User hierarchy (Customer, Admin).
 */
@Service
public class UserService {

    private static final String USERS_FILE = "users.txt";
    private final FileHandler fileHandler;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * CREATE — Register a new user
     */
    public User registerUser(String name, String email, String password, String phone,
                             String address, String city, String role) {
        // Check if email already exists
        if (findByEmail(email) != null) {
            return null; // Email already registered
        }

        int id = fileHandler.getNextId(USERS_FILE);
        String hashedPassword = passwordEncoder.encode(password);
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        User user;
        if ("admin".equalsIgnoreCase(role)) {
            Admin admin = new Admin(id, name, email, hashedPassword, phone,
                    address, city, true, now, "General", "FULL");
            user = admin;
        } else {
            Customer customer = new Customer(id, name, email, hashedPassword, phone,
                    address, city, true, now, "REGULAR", 0);
            user = customer;
        }

        fileHandler.writeLine(USERS_FILE, user.toFileString());
        return user;
    }

    /**
     * READ — Find user by email
     */
    public User findByEmail(String email) {
        List<String> lines = fileHandler.readAllLines(USERS_FILE);
        for (String line : lines) {
            String[] parts = line.split("\\|", -1);
            if (parts.length >= 10 && parts[2].equalsIgnoreCase(email)) {
                return parseUser(line);
            }
        }
        return null;
    }

    /**
     * READ — Find user by ID
     */
    public User findById(int id) {
        String line = fileHandler.findById(USERS_FILE, String.valueOf(id));
        if (line != null) {
            return parseUser(line);
        }
        return null;
    }

    /**
     * READ — Get all users
     */
    public List<User> getAllUsers() {
        return fileHandler.readAllLines(USERS_FILE).stream()
                .map(this::parseUser)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * READ — Get all customers
     */
    public List<Customer> getAllCustomers() {
        return getAllUsers().stream()
                .filter(u -> u instanceof Customer)
                .map(u -> (Customer) u)
                .collect(Collectors.toList());
    }

    /**
     * READ — Search users by keyword
     */
    public List<User> searchUsers(String keyword) {
        return fileHandler.searchLines(USERS_FILE, keyword).stream()
                .map(this::parseUser)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * UPDATE — Update user profile
     */
    public boolean updateUser(User user) {
        return fileHandler.updateLine(USERS_FILE, String.valueOf(user.getId()), user.toFileString());
    }

    /**
     * DELETE — Delete user by ID
     */
    public boolean deleteUser(int id) {
        return fileHandler.deleteLine(USERS_FILE, String.valueOf(id));
    }

    /**
     * Authenticate user — Login
     */
    public User authenticate(String email, String password) {
        User user = findByEmail(email);
        if (user != null && user.isActive() && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * Change password
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        User user = findById(userId);
        if (user != null && passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            return updateUser(user);
        }
        return false;
    }

    /**
     * Parse a file line into the appropriate User subclass (Polymorphism)
     */
    private User parseUser(String line) {
        try {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 10) return null;

            String role = parts[9];
            if ("admin".equalsIgnoreCase(role)) {
                return Admin.fromFileString(line);
            } else {
                return Customer.fromFileString(line);
            }
        } catch (Exception e) {
            System.err.println("Error parsing user line: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get total user count
     */
    public int getUserCount() {
        return fileHandler.getRecordCount(USERS_FILE);
    }
}
