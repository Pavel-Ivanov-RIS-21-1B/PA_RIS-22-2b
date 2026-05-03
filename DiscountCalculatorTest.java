package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscountCalculatorTest {
    
    @Test
    void testApplyDiscount() {
        assertEquals(90.0, DiscountCalculator.applyDiscount(100, 10));
        assertEquals(75.5, DiscountCalculator.applyDiscount(100, 24.5));
        assertEquals(0.0, DiscountCalculator.applyDiscount(100, 100));
    }
    
    @Test
    void testApplyDiscountThrowsOnInvalidPrice() {
        assertThrows(IllegalArgumentException.class, 
            () -> DiscountCalculator.applyDiscount(-10, 10));
        assertThrows(IllegalArgumentException.class, 
            () -> DiscountCalculator.applyDiscount(0, 10));
    }
    
    @Test
    void testApplyDiscountThrowsOnInvalidDiscount() {
        assertThrows(IllegalArgumentException.class, 
            () -> DiscountCalculator.applyDiscount(100, -5));
        assertThrows(IllegalArgumentException.class, 
            () -> DiscountCalculator.applyDiscount(100, 150));
    }
}
