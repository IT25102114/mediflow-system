package com.mediflow.model;

/**
 * ABSTRACTION + ENCAPSULATION
 * 
 * Abstract base class for all product/medicine types.
 * Demonstrates:
 * - Encapsulation: Private fields with getters/setters
 * - Abstraction: Abstract methods for subclass-specific behavior
 * - Inheritance: PrescriptionMedicine and OTCMedicine extend this
 */
public abstract class Product {
    private int id;
    private String name;
    private String description;
    private int categoryId;
    private String categoryName; // Transient, for display purposes
    private String manufacturer;
    private double price;
    private double comparePrice;
    private int stockQuantity;
    private int lowStockThreshold;
    private String imageUrl;
    private boolean active;
    private boolean featured;
    private String sku;
    private String dosage;
    private String sideEffects;
    private String usageInstructions;
    private String weight;
    private String expiryDate;
    private String batchNumber;
    private int totalSold;
    private double rating;
    private int reviewCount;
    private String createdAt;

    public Product() {
        this.active = true;
        this.featured = false;
        this.stockQuantity = 0;
        this.lowStockThreshold = 10;
        this.totalSold = 0;
        this.rating = 0.0;
        this.reviewCount = 0;
    }

    // ===== ABSTRACT METHODS =====

    /**
     * Returns the product type ("PRESCRIPTION" or "OTC")
     * Polymorphism: each subclass returns its own type
     */
    public abstract String getProductType();

    /**
     * Returns whether a prescription is required
     * Polymorphism: PrescriptionMedicine returns true, OTCMedicine returns false
     */
    public abstract boolean requiresPrescription();

    /**
     * Returns formatted display information
     * Polymorphism: different display for each medicine type
     */
    public abstract String getDisplayInfo();

    /**
     * Calculates the effective price after any type-specific adjustments
     * Polymorphism: prescription medicines may have different pricing logic
     */
    public abstract double getEffectivePrice();

    /**
     * Converts product data to pipe-delimited string for file storage
     */
    public abstract String toFileString();

    // ===== COMMON METHODS =====

    public boolean isLowStock() {
        return stockQuantity <= lowStockThreshold;
    }

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public int getDiscountPercentage() {
        if (comparePrice > 0 && comparePrice > price) {
            return (int) Math.round(((comparePrice - price) / comparePrice) * 100);
        }
        return 0;
    }

    public String getFormattedPrice() {
        return price == (int) price ? String.valueOf((int) price) : String.format("%.2f", price);
    }

    public String getFormattedComparePrice() {
        return comparePrice == (int) comparePrice ? String.valueOf((int) comparePrice) : String.format("%.2f", comparePrice);
    }

    // ===== GETTERS AND SETTERS =====

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getComparePrice() { return comparePrice; }
    public void setComparePrice(double comparePrice) { this.comparePrice = comparePrice; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public int getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(int lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getSideEffects() { return sideEffects; }
    public void setSideEffects(String sideEffects) { this.sideEffects = sideEffects; }

    public String getUsageInstructions() { return usageInstructions; }
    public void setUsageInstructions(String usageInstructions) { this.usageInstructions = usageInstructions; }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }

    public int getTotalSold() { return totalSold; }
    public void setTotalSold(int totalSold) { this.totalSold = totalSold; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return getDisplayInfo();
    }
}
