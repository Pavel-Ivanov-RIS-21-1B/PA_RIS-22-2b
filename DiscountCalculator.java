package com.example;

public class DiscountCalculator {
    
    public static double applyDiscount(double price, double discountPercent) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        
        double discountAmount = price * discountPercent / 100;
        return Math.round((price - discountAmount) * 100.0) / 100.0;
    }
}
