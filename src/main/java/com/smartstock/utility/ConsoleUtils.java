package com.smartstock.utility;

/**
 * Utility helper class for console formatting, headers, banners, and layout dividers.
 */
public final class ConsoleUtils {

    private ConsoleUtils() {
        // Prevent instantiation
    }

    public static void printBanner() {
        System.out.println("==================================================================");
        System.out.println("       SMARTSTOCK - AI INVESTMENT & VIRTUAL TRADING PLATFORM      ");
        System.out.println("                   7-Day Engineering Sprint                       ");
        System.out.println("==================================================================");
    }

    public static void printSectionHeader(String title) {
        System.out.println("\n--------------------------------------------------");
        System.out.println(" >>> " + title.toUpperCase());
        System.out.println("--------------------------------------------------");
    }

    public static void printDivider() {
        System.out.println("--------------------------------------------------");
    }

    public static void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    public static void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void printWarning(String message) {
        System.out.println("[WARNING] " + message);
    }

    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static String formatCurrency(double amount) {
        return String.format("$%,.2f", amount);
    }
}
