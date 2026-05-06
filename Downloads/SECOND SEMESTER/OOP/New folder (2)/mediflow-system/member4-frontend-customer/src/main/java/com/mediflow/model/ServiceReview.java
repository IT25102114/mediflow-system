package com.mediflow.model;

/**
 * INHERITANCE + POLYMORPHISM
 * 
 * ServiceReview extends Review for overall store/service reviews.
 * Different from ProductReview (Polymorphism) — not tied to a specific product.
 */
public class ServiceReview extends Review {
    private String serviceType; // DELIVERY, CUSTOMER_SUPPORT, OVERALL
    private int orderId; // Optional, linked to a specific order

    public ServiceReview() {
        super();
        this.serviceType = "OVERALL";
    }

    @Override
    public String getReviewType() {
        return "SERVICE";
    }

    @Override
    public String getDisplayInfo() {
        return String.format("[%s] %s — %s by %s on %s",
                serviceType, getStarRating(), getComment(), getUserName(), getCreatedAt());
    }

    @Override
    public String toFileString() {
        return String.join("|",
                String.valueOf(getId()),
                String.valueOf(getUserId()),
                getUserName() != null ? getUserName() : "",
                String.valueOf(getRating()),
                getComment() != null ? getComment() : "",
                getCreatedAt() != null ? getCreatedAt() : "",
                String.valueOf(isApproved()),
                "SERVICE",
                serviceType != null ? serviceType : "OVERALL",
                String.valueOf(orderId),
                "" // padding
        );
    }

    public static ServiceReview fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 10) return null;

        ServiceReview r = new ServiceReview();
        r.setId(Integer.parseInt(p[0]));
        r.setUserId(Integer.parseInt(p[1]));
        r.setUserName(p[2]);
        r.setRating(Integer.parseInt(p[3]));
        r.setComment(p[4]);
        r.setCreatedAt(p[5]);
        r.setApproved(Boolean.parseBoolean(p[6]));
        // p[7] = type
        r.setServiceType(p[8]);
        r.setOrderId(p[9].isEmpty() ? 0 : Integer.parseInt(p[9]));
        return r;
    }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
}
