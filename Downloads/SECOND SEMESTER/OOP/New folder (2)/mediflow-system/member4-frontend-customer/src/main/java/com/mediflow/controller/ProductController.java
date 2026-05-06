package com.mediflow.controller;

import com.mediflow.model.*;
import com.mediflow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;
    @Autowired private ReviewService reviewService;

    @GetMapping
    public String listProducts(@RequestParam(required = false) String search,
                               @RequestParam(required = false) Integer category,
                               Model model) {
        List<Product> products;
        if (search != null && !search.isEmpty()) {
            products = productService.searchProducts(search);
            model.addAttribute("search", search);
        } else if (category != null) {
            products = productService.getProductsByCategory(category);
            model.addAttribute("selectedCategory", category);
        } else {
            products = productService.getAllProducts();
        }
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products";
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        if (product == null) return "redirect:/products";
        Category cat = categoryService.findById(product.getCategoryId());
        if (cat != null) product.setCategoryName(cat.getName());
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviewService.getReviewsByProduct(id));
        model.addAttribute("isPrescription", product instanceof PrescriptionMedicine);
        return "product-detail";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product-form";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam String name, @RequestParam String description,
                             @RequestParam int categoryId, @RequestParam String manufacturer,
                             @RequestParam double price,
                             @RequestParam(defaultValue = "0") double comparePrice,
                             @RequestParam int stockQuantity,
                             @RequestParam(required = false) String dosage,
                             @RequestParam(required = false) String sideEffects,
                             @RequestParam(required = false) String usageInstructions,
                             @RequestParam(required = false) String weight,
                             @RequestParam(required = false) String expiryDate,
                             @RequestParam(required = false) String batchNumber,
                             @RequestParam(required = false) String sku,
                             @RequestParam(defaultValue = "false") boolean featured,
                             @RequestParam String productType,
                             @RequestParam(required = false) String imageUrl,
                             @RequestParam(required = false) String extra1,
                             @RequestParam(required = false) String extra2,
                             @RequestParam(required = false) String extra3,
                             RedirectAttributes ra) {
        productService.addProduct(name, description, categoryId, manufacturer, price,
                comparePrice, stockQuantity, dosage, sideEffects, usageInstructions,
                weight, expiryDate, batchNumber, sku, featured, productType, imageUrl,
                extra1, extra2, extra3);
        ra.addFlashAttribute("success", "Product added successfully!");
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        Product product = productService.findById(id);
        if (product == null) return "redirect:/products";
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("editing", true);
        return "product-form";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable int id,
                                @RequestParam String name, @RequestParam String description,
                                @RequestParam int categoryId, @RequestParam String manufacturer,
                                @RequestParam double price,
                                @RequestParam(defaultValue = "0") double comparePrice,
                                @RequestParam int stockQuantity,
                                @RequestParam(required = false) String dosage,
                                @RequestParam(required = false) String sideEffects,
                                @RequestParam(required = false) String usageInstructions,
                                @RequestParam(required = false) String weight,
                                @RequestParam(required = false) String expiryDate,
                                @RequestParam(required = false) String batchNumber,
                                @RequestParam(required = false) String sku,
                                @RequestParam(defaultValue = "false") boolean featured,
                                RedirectAttributes ra) {
        Product product = productService.findById(id);
        if (product == null) return "redirect:/products";
        product.setName(name); product.setDescription(description);
        product.setCategoryId(categoryId); product.setManufacturer(manufacturer);
        product.setPrice(price); product.setComparePrice(comparePrice);
        product.setStockQuantity(stockQuantity); product.setDosage(dosage);
        product.setSideEffects(sideEffects); product.setUsageInstructions(usageInstructions);
        product.setWeight(weight); product.setExpiryDate(expiryDate);
        product.setBatchNumber(batchNumber); product.setSku(sku); product.setFeatured(featured);
        productService.updateProduct(product);
        ra.addFlashAttribute("success", "Product updated!");
        return "redirect:/products/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, RedirectAttributes ra) {
        productService.deleteProduct(id);
        ra.addFlashAttribute("success", "Product deleted!");
        return "redirect:/products";
    }
}
