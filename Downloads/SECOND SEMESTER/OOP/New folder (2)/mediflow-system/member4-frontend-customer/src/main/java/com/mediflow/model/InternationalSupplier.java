package com.mediflow.model;

/**
 * INHERITANCE + POLYMORPHISM
 * 
 * InternationalSupplier extends Supplier for overseas suppliers.
 * Different display and fields compared to LocalSupplier (Polymorphism).
 */
public class InternationalSupplier extends Supplier {
    private String country;
    private String importLicenseNumber;
    private String currency;

    public InternationalSupplier() {
        super();
        this.currency = "USD";
    }

    @Override
    public String getSupplierType() {
        return "INTERNATIONAL";
    }

    @Override
    public String getDisplayInfo() {
        return String.format("[International] %s - %s (%s) | License: %s",
                getName(), country, currency, importLicenseNumber);
    }

    @Override
    public String toFileString() {
        return String.join("|",
                String.valueOf(getId()),
                getName() != null ? getName() : "",
                getContactPhone() != null ? getContactPhone() : "",
                getEmail() != null ? getEmail() : "",
                getAddress() != null ? getAddress() : "",
                String.valueOf(isActive()),
                getCreatedAt() != null ? getCreatedAt() : "",
                "INTERNATIONAL",
                country != null ? country : "",
                importLicenseNumber != null ? importLicenseNumber : "",
                currency != null ? currency : "USD"
        );
    }

    public static InternationalSupplier fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 11) return null;

        InternationalSupplier s = new InternationalSupplier();
        s.setId(Integer.parseInt(p[0]));
        s.setName(p[1]);
        s.setContactPhone(p[2]);
        s.setEmail(p[3]);
        s.setAddress(p[4]);
        s.setActive(Boolean.parseBoolean(p[5]));
        s.setCreatedAt(p[6]);
        // p[7] = type
        s.setCountry(p[8]);
        s.setImportLicenseNumber(p[9]);
        s.setCurrency(p[10]);
        return s;
    }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getImportLicenseNumber() { return importLicenseNumber; }
    public void setImportLicenseNumber(String importLicenseNumber) { this.importLicenseNumber = importLicenseNumber; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
