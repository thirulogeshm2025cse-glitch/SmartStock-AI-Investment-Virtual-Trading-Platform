package com.smartstock.utility;

import java.util.regex.Pattern;

/**
 * Utility class to validate console user inputs.
 */
public final class InputValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern SYMBOL_PATTERN =
            Pattern.compile("^[A-Za-z]{1,10}$");

    private InputValidator() {
        // Prevent instantiation
    }

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidUserId(String userId) {
        return userId != null && !userId.trim().isEmpty();
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidUsername(String username) {
        return username != null && username.trim().length() >= 3;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.trim().length() >= 4;
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidSymbol(String symbol) {
        return symbol != null && SYMBOL_PATTERN.matcher(symbol.trim()).matches();
    }

    public static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0.0;
    }

    public static boolean isValidInitialBalance(double balance) {
        return balance > 0.0;
    }
}
