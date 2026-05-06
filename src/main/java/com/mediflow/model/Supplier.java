package com.mediflow.model;

/**
 * ABSTRACTION + ENCAPSULATION
 * 
 * Abstract base class for suppliers/manufacturers.
 * Demonstrates abstraction with abstract methods and encapsulation with private fields.
 */
public abstract class Supplier {
    private int id;
    private String name;
    private String contactPhone;
    private String email;
    private String address;
    private boolean active;
    private String createdAt;

    public Supplier() {
        this.active = true;
    }

    // ===== ABSTRACT METHODS =====

    public abstract String getSupplierType();
    public abstract String getDisplayInfo();
    public abstract String toFileString();

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() { return getDisplayInfo(); }
}
