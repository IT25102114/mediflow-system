package com.mediflow.controller;

import com.mediflow.model.Product;
import com.mediflow.service.CategoryService;
import com.mediflow.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        List<Product> featured = productService.getFeaturedProducts();
        model.addAttribute("featuredProducts", featured);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "home";
    }
}
