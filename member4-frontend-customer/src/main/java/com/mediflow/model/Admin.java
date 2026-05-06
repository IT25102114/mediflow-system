package com.mediflow.model;

/**
 * INHERITANCE + POLYMORPHISM
 * 
 * Admin class extends User (Inheritance).
 * Overrides abstract methods with admin-specific behavior (Polymorphism).
 * 
 * Additional fields specific to admins:
 * - department: which department the admin manages
 * - accessLevel: FULL, LIMITED
 */
public class Admin extends User {
    private String department;
    private String accessLevel; // FULL, LIMITED

    public Admin() {
        super();
        this.accessLevel = "FULL";
    }

    public Admin(int id, String name, String email, String password, String phone,
                 String address, String city, boolean active, String createdAt,
                 String department, String accessLevel) {
        super(id, name, email, password, phone, address, city, active, createdAt);
        this.department = department;
        this.accessLevel = accessLevel;
    }

    // ===== POLYMORPHISM — Overriding abstract methods =====

    @Override
    public String getRole() {
        return "admin";
    }

    @Override
    public String getDisplayInfo() {
        return String.format("Admin: %s (%s) - Dept: %s, Access: %s",
                getName(), getEmail(), department, accessLevel);
    }

    @Override
    public String toFileString() {
        return String.join("|",
                String.valueOf(getId()),
                getName(),
                getEmail(),
                getPassword(),
                getPhone() != null ? getPhone() : "",
                getAddress() != null ? getAddress() : "",
                getCity() != null ? getCity() : "",
                String.valueOf(isActive()),
                getCreatedAt() != null ? getCreatedAt() : "",
                "admin",
                department != null ? department : "",
                accessLevel != null ? accessLevel : "FULL"
        );
    }

    /**
     * ABSTRACTION — Admin-only method not available to regular users
     */
    public boolean hasFullAccess() {
        return "FULL".equals(accessLevel);
    }

    /**
     * Factory method to create Admin from a pipe-delimited file line
     */
    public static Admin fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 12) return null;

        Admin admin = new Admin();
        admin.setId(Integer.parseInt(parts[0]));
        admin.setName(parts[1]);
        admin.setEmail(parts[2]);
        admin.setPassword(parts[3]);
        admin.setPhone(parts[4]);
        admin.setAddress(parts[5]);
        admin.setCity(parts[6]);
        admin.setActive(Boolean.parseBoolean(parts[7]));
        admin.setCreatedAt(parts[8]);
        // parts[9] = role (skip)
        admin.setDepartment(parts[10]);
        admin.setAccessLevel(parts[11]);
        return admin;
    }

    // ===== GETTERS AND SETTERS =====

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
