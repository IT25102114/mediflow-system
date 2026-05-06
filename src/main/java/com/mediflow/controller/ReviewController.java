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
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired private ReviewService reviewService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "reviews";
    }

    @GetMapping("/add")
    public String addForm(@RequestParam(required = false) Integer productId,
                          @RequestParam(required = false) String productName,
                          Model model) {
        model.addAttribute("productId", productId);
        model.addAttribute("productName", productName);
        return "review-form";
    }

    @PostMapping("/add")
    public String add(@RequestParam int productId, @RequestParam String productName,
                      @RequestParam int rating, @RequestParam String comment,
                      HttpSession session, RedirectAttributes ra) {
        Integer userId = (Integer) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName");
        if (userId == null) return "redirect:/login";
        reviewService.addProductReview(userId, userName, productId, productName, rating, comment, true);
        ra.addFlashAttribute("success", "Review submitted!");
        return "redirect:/products/" + productId;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        reviewService.deleteReview(id);
        ra.addFlashAttribute("success", "Review deleted!");
        return "redirect:/reviews";
    }
}
