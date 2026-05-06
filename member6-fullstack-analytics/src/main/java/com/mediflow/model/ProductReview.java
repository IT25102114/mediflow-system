package com.mediflow.model;

/**
 * INHERITANCE + POLYMORPHISM
 * 
 * ProductReview extends Review for medicine/product-specific reviews.
 */
public class ProductReview extends Review {
    private int productId;
    private String productName; // Transient
    private boolean verified; // Verified purchase

    public ProductReview() {
        super();
        this.verified = false;
    }

    @Override
    public String getReviewType() {
        return "PRODUCT";
    }

    @Override
    public String getDisplayInfo() {
        String badge = verified ? "[Verified] " : "";
        return String.format("%s%s — %s by %s on %s",
                badge, getStarRating(), getComment(), getUserName(), getCreatedAt());
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
                "PRODUCT",
                String.valueOf(productId),
                productName != null ? productName : "",
                String.valueOf(verified)
        );
    }

    public static ProductReview fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 11) return null;

        ProductReview r = new ProductReview();
        r.setId(Integer.parseInt(p[0]));
        r.setUserId(Integer.parseInt(p[1]));
        r.setUserName(p[2]);
        r.setRating(Integer.parseInt(p[3]));
        r.setComment(p[4]);
        r.setCreatedAt(p[5]);
        r.setApproved(Boolean.parseBoolean(p[6]));
        // p[7] = type
        r.setProductId(Integer.parseInt(p[8]));
        r.setProductName(p[9]);
        r.setVerified(Boolean.parseBoolean(p[10]));
        return r;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
}
