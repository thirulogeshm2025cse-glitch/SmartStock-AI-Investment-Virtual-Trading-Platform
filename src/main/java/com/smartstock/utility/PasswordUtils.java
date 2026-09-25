package com.smartstock.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing and verifying user passwords using SHA-256.
 */
public final class PasswordUtils {

    private PasswordUtils() {
        // Prevent instantiation
    }

    /**
     * Hashes a plain-text password using SHA-256.
     *
     * @param password Plain-text password to hash
     * @return Hexadecimal representation of the SHA-256 hash
     */
    public static String hashPassword(String password) {
        if (password == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available in current JVM", e);
        }
    }

    /**
     * Verifies that a raw password matches a stored SHA-256 hashed password.
     *
     * @param rawPassword    The plain-text password entered by the user
     * @param hashedPassword The stored SHA-256 hash
     * @return true if credentials match, false otherwise
     */
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }
        String computedHash = hashPassword(rawPassword);
        return computedHash.equalsIgnoreCase(hashedPassword);
    }
}
