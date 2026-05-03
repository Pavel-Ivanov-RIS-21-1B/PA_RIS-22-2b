package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceIntegrationTest {
    
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
    }
    
    @Test
    void testAddAndGetUser() {
        userService.addUser("john_doe", "john@example.com");
        assertEquals("john@example.com", userService.getUserEmail("john_doe"));
    }
    
    @Test
    void testCalculateUserDiscountIntegration() {
        // Интеграционный тест: проверяет взаимодействие UserService и DiscountCalculator
        double result = userService.calculateUserDiscount(200, 15);
        assertEquals(170.0, result);
    }
    
    @Test
    void testAddUserThrowsOnNull() {
        assertThrows(IllegalArgumentException.class, 
            () -> userService.addUser(null, "email@test.com"));
        assertThrows(IllegalArgumentException.class, 
            () -> userService.addUser("username", null));
    }
}
