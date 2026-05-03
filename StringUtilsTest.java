package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {
    
    @Test
    void testIsPalindrome() {
        assertTrue(StringUtils.isPalindrome("A man, a plan, a canal: Panama"));
        assertTrue(StringUtils.isPalindrome("racecar"));
        assertFalse(StringUtils.isPalindrome("hello"));
        assertFalse(StringUtils.isPalindrome(null));
        assertFalse(StringUtils.isPalindrome(""));
    }
    
    @Test
    void testCountVowels() {
        assertEquals(5, StringUtils.countVowels("Hello World"));
        assertEquals(3, StringUtils.countVowels("JavaScript"));
        assertEquals(0, StringUtils.countVowels("xyz"));
        assertEquals(0, StringUtils.countVowels(null));
    }
}
