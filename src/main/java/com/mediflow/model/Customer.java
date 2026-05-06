package com.mediflow.model;

/**
 * INHERITANCE + POLYMORPHISM
 * 
 * Customer class extends User (Inheritance).
 * Overrides abstract methods with customer-specific behavior (Polymorphism).
 * 
 * Additional fields specific to customers:
 * - membershipType: REGULAR, PREMIUM, VIP
 * - totalOrders: track customer's order count
 */
public class Customer extends User {
    private String membershipType; // REGULAR, PREMIUM, VIP
    private int totalOrders;

    public Customer() {
        super();
        this.membershipType = "REGULAR";
        this.totalOrders = 0;
    }

    public Customer(int id, String name, String email, String password, String phone,
                    String address, String city, boolean active, String createdAt,
                    String membershipType, int totalOrders) {
        super(id, name, email, password, phone, address, city, active, createdAt);
        this.membershipType = membershipType;
        this.totalOrders = totalOrders;
    }

    // ===== POLYMORPHISM — Overriding abstract methods =====

    @Override
    public String getRole() {
        return "customer";
    }

    @Override
    public String getDisplayInfo() {
        return String.format("Customer: %s (%s) - %s Member, Orders: %d",
                getName(), getEmail(), membershipType, totalOrders);
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
                "customer",
                membershipType,
                String.valueOf(totalOrders)
        );
    }

    /**
     * POLYMORPHISM — Different discount rates based on membership type
     */
    public double getDiscountRate() {
        return switch (membershipType) {
            case "PREMIUM" -> 0.10; // 10% discount
            case "VIP" -> 0.20;     // 20% discount
            default -> 0.0;         // No discount for REGULAR
        };
    }

    /**
     * Factory method to create Customer from a pipe-delimited file line
     */
    public static Customer fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 12) return null;

        Customer customer = new Customer();
        customer.setId(Integer.parseInt(parts[0]));
        customer.setName(parts[1]);
        customer.setEmail(parts[2]);
        customer.setPassword(parts[3]);
        customer.setPhone(parts[4]);
        customer.setAddress(parts[5]);
        customer.setCity(parts[6]);
        customer.setActive(Boolean.parseBoolean(parts[7]));
        customer.setCreatedAt(parts[8]);
        // parts[9] = role (skip, we know it's customer)
        customer.setMembershipType(parts[10]);
        customer.setTotalOrders(Integer.parseInt(parts[11]));
        return customer;
    }

    // ===== GETTERS AND SETTERS =====

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
}
