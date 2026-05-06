package com.mediflow.controller;

import com.mediflow.model.*;
import com.mediflow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired private OrderService orderService;
    @Autowired private ProductService productService;
    @Autowired private UserService userService;

    @GetMapping
    public String listOrders(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        String role = (String) session.getAttribute("userRole");
        List<Order> orders;
        if ("admin".equals(role)) {
            orders = orderService.getAllOrders();
        } else {
            orders = orderService.getOrdersByUserId(userId);
        }
        // Attach customer names
        for (Order o : orders) {
            User u = userService.findById(o.getUserId());
            if (u != null) o.setCustomerName(u.getName());
        }
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable int id, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        Order order = orderService.findById(id);
        if (order == null) return "redirect:/orders";
        User u = userService.findById(order.getUserId());
        if (u != null) order.setCustomerName(u.getName());
        model.addAttribute("order", order);
        return "order-detail";
    }

    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "checkout";
    }

    @PostMapping("/place")
    public String placeOrder(@RequestParam String shippingAddress,
                             @RequestParam String shippingCity,
                             @RequestParam String shippingPhone,
                             @RequestParam String paymentMethod,
                             @RequestParam(required = false) String notes,
                             @RequestParam String productIds,
                             @RequestParam String quantities,
                             HttpSession session, RedirectAttributes ra) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        String[] pids = productIds.split(",");
        String[] qtys = quantities.split(",");
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < pids.length; i++) {
            Product p = productService.findById(Integer.parseInt(pids[i].trim()));
            if (p != null) {
                OrderItem item = new OrderItem();
                item.setProductId(p.getId());
                item.setProductName(p.getName());
                item.setQuantity(Integer.parseInt(qtys[i].trim()));
                item.setUnitPrice(p.getPrice());
                items.add(item);
            }
        }

        if (items.isEmpty()) {
            ra.addFlashAttribute("error", "No valid products in order");
            return "redirect:/products";
        }

        Order order = orderService.createOrder(userId, shippingAddress, shippingCity,
                shippingPhone, paymentMethod, items, notes);
        ra.addFlashAttribute("success", "Order placed! Order #" + order.getOrderNumber());
        return "redirect:/orders/" + order.getId();
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable int id, @RequestParam String status,
                               RedirectAttributes ra) {
        orderService.updateOrderStatus(id, status);
        ra.addFlashAttribute("success", "Order status updated to: " + status);
        return "redirect:/orders/" + id;
    }

    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable int id, RedirectAttributes ra) {
        orderService.cancelOrder(id);
        ra.addFlashAttribute("success", "Order cancelled");
        return "redirect:/orders";
    }
}
