package com.example;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, String> userDatabase = new HashMap<>();
    private DiscountCalculator discountCalculator = new DiscountCalculator();
    
    public void addUser(String username, String email) {
        if (username == null || email == null) {
            throw new IllegalArgumentException("Username and email cannot be null");
        }
        userDatabase.put(username, email);
    }
    
    public String getUserEmail(String username) {
        return userDatabase.get(username);
    }
    
    public double calculateUserDiscount(double price, double discountPercent) {
        // Интеграция с DiscountCalculator
        return DiscountCalculator.applyDiscount(price, discountPercent);
    }
}
