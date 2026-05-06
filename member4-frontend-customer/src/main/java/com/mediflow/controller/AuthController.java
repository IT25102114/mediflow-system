package com.mediflow.controller;

import com.mediflow.model.User;
import com.mediflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("user") != null) return "redirect:/";
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession session, RedirectAttributes redirectAttributes) {
        User user = userService.authenticate(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userRole", user.getRole());
            if ("admin".equals(user.getRole())) return "redirect:/admin/dashboard";
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("error", "Invalid email or password");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        if (session.getAttribute("user") != null) return "redirect:/";
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email,
                           @RequestParam String password, @RequestParam String phone,
                           @RequestParam(required = false) String address,
                           @RequestParam(required = false) String city,
                           RedirectAttributes redirectAttributes) {
        User existing = userService.findByEmail(email);
        if (existing != null) {
            redirectAttributes.addFlashAttribute("error", "Email already registered");
            return "redirect:/register";
        }
        userService.registerUser(name, email, password, phone, address, city, "customer");
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/account")
    public String accountPage(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "account";
    }

    @PostMapping("/account/update")
    public String updateAccount(@RequestParam String name, @RequestParam String phone,
                                @RequestParam(required = false) String address,
                                @RequestParam(required = false) String city,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";
        user.setName(name); user.setPhone(phone);
        user.setAddress(address); user.setCity(city);
        userService.updateUser(user);
        session.setAttribute("user", user);
        session.setAttribute("userName", user.getName());
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/account";
    }

    private User getLoggedInUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return null;
        return userService.findById(userId);
    }
}
