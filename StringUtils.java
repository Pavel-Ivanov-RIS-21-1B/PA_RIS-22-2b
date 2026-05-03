package com.example;

public class StringUtils {
    
    public static boolean isPalindrome(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        String cleaned = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }
    
    public static int countVowels(String str) {
        if (str == null) {
            return 0;
        }
        
        return (int) str.toLowerCase().chars()
            .filter(c -> "aeiou".indexOf(c) != -1)
            .count();
    }
}
