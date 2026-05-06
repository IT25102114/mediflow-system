package com.mediflow.model;

/**
 * ENCAPSULATION
 * 
 * Represents a single item within an order.
 */
public class OrderItem {
    private int id;
    private int orderId;
    private int productId;
    private String productName; // Transient for display
    private int quantity;
    private double unitPrice;
    private double totalPrice;

    public OrderItem() {}

    public OrderItem(int id, int orderId, int productId, String productName,
                     int quantity, double unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
    }

    public String toFileString() {
        return String.join("|",
                String.valueOf(id),
                String.valueOf(orderId),
                String.valueOf(productId),
                productName != null ? productName : "",
                String.valueOf(quantity),
                String.valueOf(unitPrice),
                String.valueOf(totalPrice)
        );
    }

    public static OrderItem fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 7) return null;

        OrderItem item = new OrderItem();
        item.setId(Integer.parseInt(p[0]));
        item.setOrderId(Integer.parseInt(p[1]));
        item.setProductId(Integer.parseInt(p[2]));
        item.setProductName(p[3]);
        item.setQuantity(Integer.parseInt(p[4]));
        item.setUnitPrice(Double.parseDouble(p[5]));
        item.setTotalPrice(Double.parseDouble(p[6]));
        return item;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
