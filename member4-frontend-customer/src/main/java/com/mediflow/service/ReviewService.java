package com.mediflow.service;

import com.mediflow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private static final String REVIEWS_FILE = "reviews.txt";
    private final FileHandler fileHandler;

    @Autowired
    public ReviewService(FileHandler fileHandler) { this.fileHandler = fileHandler; }

    public Review addProductReview(int userId, String userName, int productId,
                                   String productName, int rating, String comment, boolean verified) {
        int id = fileHandler.getNextId(REVIEWS_FILE);
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ProductReview r = new ProductReview();
        r.setId(id); r.setUserId(userId); r.setUserName(userName);
        r.setRating(rating); r.setComment(comment); r.setCreatedAt(now);
        r.setProductId(productId); r.setProductName(productName); r.setVerified(verified);
        fileHandler.writeLine(REVIEWS_FILE, r.toFileString());
        return r;
    }

    public Review addServiceReview(int userId, String userName, int rating,
                                   String comment, String serviceType, int orderId) {
        int id = fileHandler.getNextId(REVIEWS_FILE);
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ServiceReview r = new ServiceReview();
        r.setId(id); r.setUserId(userId); r.setUserName(userName);
        r.setRating(rating); r.setComment(comment); r.setCreatedAt(now);
        r.setServiceType(serviceType); r.setOrderId(orderId);
        fileHandler.writeLine(REVIEWS_FILE, r.toFileString());
        return r;
    }

    public List<Review> getAllReviews() {
        return fileHandler.readAllLines(REVIEWS_FILE).stream()
                .map(this::parseReview).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<Review> getReviewsByProduct(int productId) {
        return getAllReviews().stream()
                .filter(r -> r instanceof ProductReview && ((ProductReview) r).getProductId() == productId)
                .collect(Collectors.toList());
    }

    public Review findById(int id) {
        String line = fileHandler.findById(REVIEWS_FILE, String.valueOf(id));
        return line != null ? parseReview(line) : null;
    }

    public boolean updateReview(Review review) {
        return fileHandler.updateLine(REVIEWS_FILE, String.valueOf(review.getId()), review.toFileString());
    }

    public boolean deleteReview(int id) {
        return fileHandler.deleteLine(REVIEWS_FILE, String.valueOf(id));
    }

    private Review parseReview(String line) {
        try {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 8) return null;
            String type = parts[7];
            if ("SERVICE".equalsIgnoreCase(type)) return ServiceReview.fromFileString(line);
            else return ProductReview.fromFileString(line);
        } catch (Exception e) { return null; }
    }

    public int getReviewCount() { return fileHandler.getRecordCount(REVIEWS_FILE); }
}
