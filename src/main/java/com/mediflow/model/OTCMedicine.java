package com.mediflow.model;

/**
 * INHERITANCE + POLYMORPHISM
 * 
 * OTCMedicine extends Product (Over-The-Counter medicines).
 * These medicines can be purchased without a prescription.
 * Overrides abstract methods with OTC-specific behavior.
 */
public class OTCMedicine extends Product {
    private String ageRestriction; // "NONE", "12+", "18+"
    private boolean herbal;

    public OTCMedicine() {
        super();
        this.ageRestriction = "NONE";
        this.herbal = false;
    }

    // ===== POLYMORPHISM — Overriding abstract methods =====

    @Override
    public String getProductType() {
        return "OTC";
    }

    @Override
    public boolean requiresPrescription() {
        return false; // OTC medicines don't need prescription
    }

    @Override
    public String getDisplayInfo() {
        String label = herbal ? "[Herbal]" : "[OTC]";
        return String.format("%s %s - %s (by %s) - Rs.%.2f",
                label, getName(), getDosage(), getManufacturer(), getPrice());
    }

    @Override
    public double getEffectivePrice() {
        // OTC medicines can have promotional discounts
        if (getComparePrice() > 0 && getComparePrice() > getPrice()) {
            return getPrice(); // Already discounted
        }
        return getPrice();
    }

    @Override
    public String toFileString() {
        return String.join("|",
                String.valueOf(getId()),
                getName() != null ? getName() : "",
                getDescription() != null ? getDescription() : "",
                String.valueOf(getCategoryId()),
                getManufacturer() != null ? getManufacturer() : "",
                String.valueOf(getPrice()),
                String.valueOf(getComparePrice()),
                String.valueOf(getStockQuantity()),
                String.valueOf(getLowStockThreshold()),
                getImageUrl() != null ? getImageUrl() : "",
                String.valueOf(isActive()),
                String.valueOf(isFeatured()),
                getSku() != null ? getSku() : "",
                getDosage() != null ? getDosage() : "",
                getSideEffects() != null ? getSideEffects() : "",
                getUsageInstructions() != null ? getUsageInstructions() : "",
                getWeight() != null ? getWeight() : "",
                getExpiryDate() != null ? getExpiryDate() : "",
                getBatchNumber() != null ? getBatchNumber() : "",
                String.valueOf(getTotalSold()),
                String.valueOf(getRating()),
                String.valueOf(getReviewCount()),
                getCreatedAt() != null ? getCreatedAt() : "",
                "OTC",
                ageRestriction != null ? ageRestriction : "NONE",
                String.valueOf(herbal),
                "" // padding to match field count
        );
    }

    /**
     * Factory method to create OTCMedicine from file line
     */
    public static OTCMedicine fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 26) return null;

        OTCMedicine med = new OTCMedicine();
        med.setId(Integer.parseInt(p[0]));
        med.setName(p[1]);
        med.setDescription(p[2]);
        med.setCategoryId(p[3].isEmpty() ? 0 : Integer.parseInt(p[3]));
        med.setManufacturer(p[4]);
        med.setPrice(Double.parseDouble(p[5]));
        med.setComparePrice(p[6].isEmpty() ? 0 : Double.parseDouble(p[6]));
        med.setStockQuantity(Integer.parseInt(p[7]));
        med.setLowStockThreshold(Integer.parseInt(p[8]));
        med.setImageUrl(p[9]);
        med.setActive(Boolean.parseBoolean(p[10]));
        med.setFeatured(Boolean.parseBoolean(p[11]));
        med.setSku(p[12]);
        med.setDosage(p[13]);
        med.setSideEffects(p[14]);
        med.setUsageInstructions(p[15]);
        med.setWeight(p[16]);
        med.setExpiryDate(p[17]);
        med.setBatchNumber(p[18]);
        med.setTotalSold(Integer.parseInt(p[19]));
        med.setRating(Double.parseDouble(p[20]));
        med.setReviewCount(Integer.parseInt(p[21]));
        med.setCreatedAt(p[22]);
        // p[23] = type (OTC)
        med.setAgeRestriction(p[24]);
        med.setHerbal(Boolean.parseBoolean(p[25]));
        return med;
    }

    // ===== GETTERS AND SETTERS =====

    public String getAgeRestriction() { return ageRestriction; }
    public void setAgeRestriction(String ageRestriction) { this.ageRestriction = ageRestriction; }

    public boolean isHerbal() { return herbal; }
    public void setHerbal(boolean herbal) { this.herbal = herbal; }
}
