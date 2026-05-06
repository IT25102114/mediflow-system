package com.mediflow.model;

/**
 * INHERITANCE + POLYMORPHISM
 * 
 * LocalSupplier extends Supplier for Sri Lankan suppliers.
 */
public class LocalSupplier extends Supplier {
    private String district;
    private String registrationNumber; // Sri Lankan business registration

    public LocalSupplier() {
        super();
    }

    @Override
    public String getSupplierType() {
        return "LOCAL";
    }

    @Override
    public String getDisplayInfo() {
        return String.format("[Local] %s - %s, %s | Tel: %s",
                getName(), getAddress(), district, getContactPhone());
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
                "LOCAL",
                district != null ? district : "",
                registrationNumber != null ? registrationNumber : ""
        );
    }

    public static LocalSupplier fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 10) return null;

        LocalSupplier s = new LocalSupplier();
        s.setId(Integer.parseInt(p[0]));
        s.setName(p[1]);
        s.setContactPhone(p[2]);
        s.setEmail(p[3]);
        s.setAddress(p[4]);
        s.setActive(Boolean.parseBoolean(p[5]));
        s.setCreatedAt(p[6]);
        // p[7] = type
        s.setDistrict(p[8]);
        s.setRegistrationNumber(p[9]);
        return s;
    }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
}
