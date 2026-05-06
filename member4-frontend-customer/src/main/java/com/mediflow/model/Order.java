package com.mediflow.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ENCAPSULATION
 * 
 * Order class representing a customer's medicine order.
 * Encapsulates order details with private fields and public getters/setters.
 */
public class Order {
    private int id;
    private String orderNumber;
    private int userId;
    private String customerName; // Transient for display
    private double subtotal;
    private double deliveryFee;
    private double discount;
    private double totalAmount;
    private String status; // pending, confirmed, processing, shipped, delivered, cancelled
    private String shippingAddress;
    private String shippingCity;
    private String shippingPhone;
    private String paymentMethod;
    private String paymentStatus; // pending, paid, failed, refunded
    private String notes;
    private String createdAt;
    private String updatedAt;

    // Transient field for display
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
        this.status = "pending";
        this.paymentStatus = "pending";
        this.deliveryFee = 0;
        this.discount = 0;
    }

    /**
     * Generate unique order number
     */
    public void generateOrderNumber() {
        this.orderNumber = "MF-" + System.currentTimeMillis();
    }

    /**
     * POLYMORPHISM-LIKE — Different status display based on status value
     */
    public String getStatusBadgeClass() {
        return switch (status) {
            case "confirmed" -> "badge-info";
            case "processing" -> "badge-warning";
            case "shipped" -> "badge-primary";
            case "delivered" -> "badge-success";
            case "cancelled" -> "badge-danger";
            default -> "badge-secondary";
        };
    }

    public String toFileString() {
        return String.join("|",
                String.valueOf(id),
                orderNumber != null ? orderNumber : "",
                String.valueOf(userId),
                String.valueOf(subtotal),
                String.valueOf(deliveryFee),
                String.valueOf(discount),
                String.valueOf(totalAmount),
                status != null ? status : "pending",
                shippingAddress != null ? shippingAddress : "",
                shippingCity != null ? shippingCity : "",
                shippingPhone != null ? shippingPhone : "",
                paymentMethod != null ? paymentMethod : "",
                paymentStatus != null ? paymentStatus : "pending",
                notes != null ? notes : "",
                createdAt != null ? createdAt : "",
                updatedAt != null ? updatedAt : ""
        );
    }

    public static Order fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 16) return null;

        Order order = new Order();
        order.setId(Integer.parseInt(p[0]));
        order.setOrderNumber(p[1]);
        order.setUserId(Integer.parseInt(p[2]));
        order.setSubtotal(Double.parseDouble(p[3]));
        order.setDeliveryFee(Double.parseDouble(p[4]));
        order.setDiscount(Double.parseDouble(p[5]));
        order.setTotalAmount(Double.parseDouble(p[6]));
        order.setStatus(p[7]);
        order.setShippingAddress(p[8]);
        order.setShippingCity(p[9]);
        order.setShippingPhone(p[10]);
        order.setPaymentMethod(p[11]);
        order.setPaymentStatus(p[12]);
        order.setNotes(p[13]);
        order.setCreatedAt(p[14]);
        order.setUpdatedAt(p[15]);
        return order;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getShippingCity() { return shippingCity; }
    public void setShippingCity(String shippingCity) { this.shippingCity = shippingCity; }

    public String getShippingPhone() { return shippingPhone; }
    public void setShippingPhone(String shippingPhone) { this.shippingPhone = shippingPhone; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
