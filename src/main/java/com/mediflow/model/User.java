package com.mediflow.model;

/**
 * ABSTRACTION + ENCAPSULATION
 * 
 * Abstract base class for all user types in the system.
 * Demonstrates:
 * - Encapsulation: All fields are private with public getters/setters
 * - Abstraction: Abstract methods that subclasses must implement
 * - Inheritance: Customer and Admin extend this class
 */
public abstract class User {
    // Encapsulated fields (private access)
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String city;
    private boolean active;
    private String createdAt;

    // Default constructor
    public User() {
        this.active = true;
    }

    // Parameterized constructor
    public User(int id, String name, String email, String password, String phone,
                String address, String city, boolean active, String createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.active = active;
        this.createdAt = createdAt;
    }

    // ===== ABSTRACT METHODS (Abstraction) =====
    // Subclasses must implement these differently (Polymorphism)

    /**
     * Returns the role of the user (e.g., "customer", "admin")
     * Each subclass provides its own implementation (Polymorphism)
     */
    public abstract String getRole();

    /**
     * Returns a formatted display string for the user
     * Different user types display differently (Polymorphism)
     */
    public abstract String getDisplayInfo();

    /**
     * Converts user data to a pipe-delimited string for file storage
     */
    public abstract String toFileString();

    // ===== GETTERS AND SETTERS (Encapsulation) =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return getDisplayInfo();
    }
}
