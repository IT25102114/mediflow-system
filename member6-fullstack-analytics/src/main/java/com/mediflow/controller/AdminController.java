package com.mediflow.controller;

import com.mediflow.model.*;
import com.mediflow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserService userService;
    @Autowired private ProductService productService;
    @Autowired private OrderService orderService;
    @Autowired private SupplierService supplierService;
    @Autowired private ReviewService reviewService;
    @Autowired private CategoryService categoryService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("userCount", userService.getUserCount());
        model.addAttribute("productCount", productService.getProductCount());
        model.addAttribute("orderCount", orderService.getOrderCount());
        model.addAttribute("supplierCount", supplierService.getSupplierCount());
        model.addAttribute("reviewCount", reviewService.getReviewCount());
        model.addAttribute("revenue", orderService.getTotalRevenue());
        model.addAttribute("recentOrders", orderService.getAllOrders().stream().limit(5).toList());
        model.addAttribute("lowStock", productService.getLowStockProducts());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id, HttpSession session, RedirectAttributes ra) {
        if (!"admin".equals(session.getAttribute("userRole"))) return "redirect:/login";
        userService.deleteUser(id);
        ra.addFlashAttribute("success", "User deleted");
        return "redirect:/admin/users";
    }

    @GetMapping("/categories")
    public String manageCategories(HttpSession session, Model model) {
        if (!"admin".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/categories";
    }

    @PostMapping("/categories/add")
    public String addCategory(@RequestParam String name, @RequestParam String description,
                              @RequestParam(required = false) String icon,
                              HttpSession session, RedirectAttributes ra) {
        if (!"admin".equals(session.getAttribute("userRole"))) return "redirect:/login";
        categoryService.addCategory(name, description, icon);
        ra.addFlashAttribute("success", "Category added");
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id, HttpSession session, RedirectAttributes ra) {
        if (!"admin".equals(session.getAttribute("userRole"))) return "redirect:/login";
        categoryService.deleteCategory(id);
        ra.addFlashAttribute("success", "Category deleted");
        return "redirect:/admin/categories";
    }
}
