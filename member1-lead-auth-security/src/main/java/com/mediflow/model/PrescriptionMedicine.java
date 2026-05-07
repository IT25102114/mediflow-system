package com.mediflow.model;

/**
 * INHERITANCE + POLYMORPHISM
 * 
 * PrescriptionMedicine extends Product.
 * Represents medicines that require a doctor's prescription.
 * Overrides abstract methods with prescription-specific behavior.
 */
public class PrescriptionMedicine extends Product {
    private String prescriptionNotes;
    private String doctorName;
    private String controlledSubstanceClass; // Schedule I-V or NONE

    public PrescriptionMedicine() {
        super();
        this.controlledSubstanceClass = "NONE";
    }

    // ===== POLYMORPHISM — Overriding abstract methods =====

    @Override
    public String getProductType() {
        return "PRESCRIPTION";
    }

    @Override
    public boolean requiresPrescription() {
        return true; // Always true for prescription medicines
    }

    @Override
    public String getDisplayInfo() {
        return String.format("[Rx] %s - %s (by %s) - Rs.%.2f [PRESCRIPTION REQUIRED]",
                getName(), getDosage(), getManufacturer(), getPrice());
    }

    @Override
    public double getEffectivePrice() {
        // Prescription medicines have no discount applied directly
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
                "PRESCRIPTION",
                prescriptionNotes != null ? prescriptionNotes : "",
                doctorName != null ? doctorName : "",
                controlledSubstanceClass != null ? controlledSubstanceClass : "NONE"
        );
    }

    /**
     * Factory method to create PrescriptionMedicine from file line
     */
    public static PrescriptionMedicine fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 27) return null;

        PrescriptionMedicine med = new PrescriptionMedicine();
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
        // p[23] = type (PRESCRIPTION)
        med.setPrescriptionNotes(p[24]);
        med.setDoctorName(p[25]);
        med.setControlledSubstanceClass(p[26]);
        return med;
    }

    // ===== GETTERS AND SETTERS =====

    public String getPrescriptionNotes() { return prescriptionNotes; }
    public void setPrescriptionNotes(String prescriptionNotes) { this.prescriptionNotes = prescriptionNotes; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getControlledSubstanceClass() { return controlledSubstanceClass; }
    public void setControlledSubstanceClass(String controlledSubstanceClass) { this.controlledSubstanceClass = controlledSubstanceClass; }
}
