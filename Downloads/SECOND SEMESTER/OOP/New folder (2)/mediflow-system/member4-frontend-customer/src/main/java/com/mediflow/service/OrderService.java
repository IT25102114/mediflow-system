package com.mediflow.service;

import com.mediflow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Order Service — Handles order CRUD operations using file I/O.
 */
@Service
public class OrderService {

    private static final String ORDERS_FILE = "orders.txt";
    private static final String ORDER_ITEMS_FILE = "order_items.txt";
    private final FileHandler fileHandler;

    @Autowired
    public OrderService(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    /**
     * CREATE — Place a new order
     */
    public Order createOrder(int userId, String shippingAddress, String shippingCity,
                             String shippingPhone, String paymentMethod,
                             List<OrderItem> items, String notes) {
        int orderId = fileHandler.getNextId(ORDERS_FILE);
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Calculate totals
        double subtotal = items.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
        double deliveryFee = subtotal > 5000 ? 0 : 300; // Free delivery over Rs.5000
        double totalAmount = subtotal + deliveryFee;

        Order order = new Order();
        order.setId(orderId);
        order.generateOrderNumber();
        order.setUserId(userId);
        order.setSubtotal(subtotal);
        order.setDeliveryFee(deliveryFee);
        order.setDiscount(0);
        order.setTotalAmount(totalAmount);
        order.setStatus("pending");
        order.setShippingAddress(shippingAddress);
        order.setShippingCity(shippingCity);
        order.setShippingPhone(shippingPhone);
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus("pending");
        order.setNotes(notes != null ? notes : "");
        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        fileHandler.writeLine(ORDERS_FILE, order.toFileString());

        // Save order items
        for (OrderItem item : items) {
            int itemId = fileHandler.getNextId(ORDER_ITEMS_FILE);
            item.setId(itemId);
            item.setOrderId(orderId);
            item.setTotalPrice(item.getUnitPrice() * item.getQuantity());
            fileHandler.writeLine(ORDER_ITEMS_FILE, item.toFileString());
        }

        order.setItems(items);
        return order;
    }

    /**
     * READ — Get all orders
     */
    public List<Order> getAllOrders() {
        return fileHandler.readAllLines(ORDERS_FILE).stream()
                .map(Order::fromFileString)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(Order::getId).reversed())
                .collect(Collectors.toList());
    }

    /**
     * READ — Get orders by user ID
     */
    public List<Order> getOrdersByUserId(int userId) {
        return getAllOrders().stream()
                .filter(o -> o.getUserId() == userId)
                .collect(Collectors.toList());
    }

    /**
     * READ — Find order by ID
     */
    public Order findById(int id) {
        String line = fileHandler.findById(ORDERS_FILE, String.valueOf(id));
        if (line != null) {
            Order order = Order.fromFileString(line);
            if (order != null) {
                order.setItems(getOrderItems(id));
            }
            return order;
        }
        return null;
    }

    /**
     * READ — Get order items for an order
     */
    public List<OrderItem> getOrderItems(int orderId) {
        return fileHandler.readAllLines(ORDER_ITEMS_FILE).stream()
                .map(OrderItem::fromFileString)
                .filter(Objects::nonNull)
                .filter(item -> item.getOrderId() == orderId)
                .collect(Collectors.toList());
    }

    /**
     * UPDATE — Update order status
     */
    public boolean updateOrderStatus(int orderId, String status) {
        Order order = findById(orderId);
        if (order != null) {
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            if ("delivered".equals(status)) {
                order.setPaymentStatus("paid");
            }
            return fileHandler.updateLine(ORDERS_FILE, String.valueOf(orderId), order.toFileString());
        }
        return false;
    }

    /**
     * DELETE — Cancel order
     */
    public boolean cancelOrder(int orderId) {
        return updateOrderStatus(orderId, "cancelled");
    }

    /**
     * Get order count
     */
    public int getOrderCount() {
        return fileHandler.getRecordCount(ORDERS_FILE);
    }

    /**
     * Get total revenue
     */
    public double getTotalRevenue() {
        return getAllOrders().stream()
                .filter(o -> "delivered".equals(o.getStatus()))
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }

    /**
     * Get orders by status
     */
    public List<Order> getOrdersByStatus(String status) {
        return getAllOrders().stream()
                .filter(o -> status.equals(o.getStatus()))
                .collect(Collectors.toList());
    }
}
