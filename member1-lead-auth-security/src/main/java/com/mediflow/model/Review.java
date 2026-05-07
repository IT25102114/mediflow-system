package com.mediflow.model;

/**
 * ABSTRACTION + ENCAPSULATION
 * 
 * Abstract base class for reviews and feedback.
 * ProductReview and ServiceReview extend this class (Inheritance).
 */
public abstract class Review {
    private int id;
    private int userId;
    private String userName; // Transient for display
    private int rating; // 1-5
    private String comment;
    private String createdAt;
    private boolean approved;

    public Review() {
        this.approved = true;
    }

    // Abstract methods
    public abstract String getReviewType();
    public abstract String getDisplayInfo();
    public abstract String toFileString();

    /**
     * Returns star representation of rating
     */
    public String getStarRating() {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stars.append(i < rating ? "★" : "☆");
        }
        return stars.toString();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = Math.max(1, Math.min(5, rating)); }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}
