package com.mediflow.service;

import com.mediflow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Product Service — Handles all medicine/product CRUD operations using file I/O.
 * Works with Product hierarchy (PrescriptionMedicine, OTCMedicine).
 */
@Service
public class ProductService {

    private static final String PRODUCTS_FILE = "products.txt";
    private final FileHandler fileHandler;

    @Autowired
    public ProductService(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    /**
     * CREATE — Add a new product/medicine
     */
    public Product addProduct(String name, String description, int categoryId, String manufacturer,
                              double price, double comparePrice, int stockQuantity, String dosage,
                              String sideEffects, String usageInstructions, String weight,
                              String expiryDate, String batchNumber, String sku,
                              boolean featured, String productType, String imageUrl,
                              String extraField1, String extraField2, String extraField3) {
        int id = fileHandler.getNextId(PRODUCTS_FILE);
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Product product;
        if ("PRESCRIPTION".equalsIgnoreCase(productType)) {
            PrescriptionMedicine med = new PrescriptionMedicine();
            med.setId(id);
            med.setName(name);
            med.setDescription(description);
            med.setCategoryId(categoryId);
            med.setManufacturer(manufacturer);
            med.setPrice(price);
            med.setComparePrice(comparePrice);
            med.setStockQuantity(stockQuantity);
            med.setDosage(dosage);
            med.setSideEffects(sideEffects);
            med.setUsageInstructions(usageInstructions);
            med.setWeight(weight);
            med.setExpiryDate(expiryDate);
            med.setBatchNumber(batchNumber);
            med.setSku(sku);
            med.setFeatured(featured);
            med.setImageUrl(imageUrl);
            med.setCreatedAt(now);
            med.setPrescriptionNotes(extraField1 != null ? extraField1 : "");
            med.setDoctorName(extraField2 != null ? extraField2 : "");
            med.setControlledSubstanceClass(extraField3 != null ? extraField3 : "NONE");
            product = med;
        } else {
            OTCMedicine med = new OTCMedicine();
            med.setId(id);
            med.setName(name);
            med.setDescription(description);
            med.setCategoryId(categoryId);
            med.setManufacturer(manufacturer);
            med.setPrice(price);
            med.setComparePrice(comparePrice);
            med.setStockQuantity(stockQuantity);
            med.setDosage(dosage);
            med.setSideEffects(sideEffects);
            med.setUsageInstructions(usageInstructions);
            med.setWeight(weight);
            med.setExpiryDate(expiryDate);
            med.setBatchNumber(batchNumber);
            med.setSku(sku);
            med.setFeatured(featured);
            med.setImageUrl(imageUrl);
            med.setCreatedAt(now);
            med.setAgeRestriction(extraField1 != null ? extraField1 : "NONE");
            med.setHerbal(extraField2 != null && Boolean.parseBoolean(extraField2));
            product = med;
        }

        fileHandler.writeLine(PRODUCTS_FILE, product.toFileString());
        return product;
    }

    /**
     * READ — Get all products
     */
    public List<Product> getAllProducts() {
        return fileHandler.readAllLines(PRODUCTS_FILE).stream()
                .map(this::parseProduct)
                .filter(Objects::nonNull)
                .filter(Product::isActive)
                .collect(Collectors.toList());
    }

    /**
     * READ — Get all products (including inactive, for admin)
     */
    public List<Product> getAllProductsAdmin() {
        return fileHandler.readAllLines(PRODUCTS_FILE).stream()
                .map(this::parseProduct)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * READ — Find product by ID
     */
    public Product findById(int id) {
        String line = fileHandler.findById(PRODUCTS_FILE, String.valueOf(id));
        return line != null ? parseProduct(line) : null;
    }

    /**
     * READ — Search products by keyword
     */
    public List<Product> searchProducts(String keyword) {
        return fileHandler.searchLines(PRODUCTS_FILE, keyword).stream()
                .map(this::parseProduct)
                .filter(Objects::nonNull)
                .filter(Product::isActive)
                .collect(Collectors.toList());
    }

    /**
     * READ — Get featured products
     */
    public List<Product> getFeaturedProducts() {
        return getAllProducts().stream()
                .filter(Product::isFeatured)
                .limit(8)
                .collect(Collectors.toList());
    }

    /**
     * READ — Get products by category
     */
    public List<Product> getProductsByCategory(int categoryId) {
        return getAllProducts().stream()
                .filter(p -> p.getCategoryId() == categoryId)
                .collect(Collectors.toList());
    }

    /**
     * UPDATE — Update product details
     */
    public boolean updateProduct(Product product) {
        return fileHandler.updateLine(PRODUCTS_FILE, String.valueOf(product.getId()), product.toFileString());
    }

    /**
     * DELETE — Soft delete (set inactive)
     */
    public boolean deleteProduct(int id) {
        Product product = findById(id);
        if (product != null) {
            product.setActive(false);
            return updateProduct(product);
        }
        return false;
    }

    /**
     * Hard delete — Remove from file
     */
    public boolean hardDeleteProduct(int id) {
        return fileHandler.deleteLine(PRODUCTS_FILE, String.valueOf(id));
    }

    /**
     * Parse a file line into the appropriate Product subclass (Polymorphism)
     */
    private Product parseProduct(String line) {
        try {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 24) return null;

            String type = parts[23];
            if ("PRESCRIPTION".equalsIgnoreCase(type)) {
                return PrescriptionMedicine.fromFileString(line);
            } else {
                return OTCMedicine.fromFileString(line);
            }
        } catch (Exception e) {
            System.err.println("Error parsing product line: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get total product count
     */
    public int getProductCount() {
        return (int) getAllProducts().stream().count();
    }

    /**
     * Get low stock products
     */
    public List<Product> getLowStockProducts() {
        return getAllProducts().stream()
                .filter(Product::isLowStock)
                .collect(Collectors.toList());
    }
}
