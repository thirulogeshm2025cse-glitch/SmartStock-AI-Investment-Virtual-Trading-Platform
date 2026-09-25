package com.smartstock.controller;

import com.smartstock.exception.AuthenticationException;
import com.smartstock.exception.InvalidUserDataException;
import com.smartstock.exception.SmartStockException;
import com.smartstock.exception.UserAlreadyExistsException;
import com.smartstock.exception.UserNotFoundException;
import com.smartstock.model.User;
import com.smartstock.service.UserService;
import com.smartstock.utility.ConsoleUtils;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Controller handling user registration, authentication, session state management,
 * and the user account dashboard.
 */
public class UserController {
    private final UserService userService;
    private final Scanner scanner;
    private User currentUser; // Holds the active user session for the application lifecycle

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public UserController(UserService userService, Scanner scanner) {
        if (userService == null) {
            throw new IllegalArgumentException("UserService cannot be null");
        }
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
        this.userService = userService;
        this.scanner = scanner;
        this.currentUser = null;
    }

    /**
     * Returns the currently authenticated user in this session.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the active session user.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Checks if there is an active logged-in user in this session.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Clears the current active session.
     */
    public void logout() {
        this.currentUser = null;
    }

    /**
     * Interactive flow for registering a new user account.
     */
    public void handleRegister() {
        ConsoleUtils.printSectionHeader("User Registration");
        System.out.println("Fill in the following details to create your virtual trading account:\n");

        System.out.print("Enter User ID (e.g., USR-101): ");
        String userId = scanner.nextLine().trim();

        System.out.print("Enter Full Name (e.g., John Doe): ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Email Address (e.g., john@example.com): ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Username (min 3 characters): ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Password (min 4 characters): ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter Initial Virtual Balance (e.g., 10000.00): ");
        String balanceInput = scanner.nextLine().trim();

        // 1. Check for empty fields at the presentation level
        if (userId.isEmpty() || name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || balanceInput.isEmpty()) {
            ConsoleUtils.printError("Validation failed: All fields are mandatory and cannot be empty.");
            return;
        }

        // 2. Validate numeric balance format
        double initialBalance;
        try {
            initialBalance = Double.parseDouble(balanceInput);
        } catch (NumberFormatException e) {
            ConsoleUtils.printError("Invalid balance value '" + balanceInput + "'. Balance must be a valid positive number.");
            return;
        }

        // 3. Dispatch to service layer with domain exception handling
        try {
            User registeredUser = userService.register(userId, name, email, username, password, initialBalance);
            ConsoleUtils.printSuccess("User registration completed successfully!");
            System.out.println("--------------------------------------------------");
            System.out.println(" User ID:         " + registeredUser.getUserId());
            System.out.println(" Full Name:       " + registeredUser.getName());
            System.out.println(" Username:        @" + registeredUser.getUsername());
            System.out.println(" Email:           " + registeredUser.getEmail());
            System.out.println(" Virtual Balance: " + ConsoleUtils.formatCurrency(registeredUser.getVirtualBalance()));
            System.out.println(" Registration:    " + registeredUser.getCreatedAt().format(DATE_FORMATTER));
            System.out.println("--------------------------------------------------");
            ConsoleUtils.printInfo("Account created. Please select Option 2 from the Main Menu to log in.");
        } catch (InvalidUserDataException e) {
            ConsoleUtils.printError("Invalid Registration Data: " + e.getMessage());
        } catch (UserAlreadyExistsException e) {
            ConsoleUtils.printError("Duplicate Account: " + e.getMessage());
        } catch (SmartStockException e) {
            ConsoleUtils.printError("Registration Error: " + e.getMessage());
        } catch (Exception e) {
            ConsoleUtils.printError("An unexpected error occurred during registration: " + e.getMessage());
        }
    }

    /**
     * Interactive flow for logging into an existing user account.
     *
     * @return true if login succeeded, false otherwise
     */
    public boolean handleLogin() {
        ConsoleUtils.printSectionHeader("User Login");

        if (isLoggedIn()) {
            ConsoleUtils.printInfo("An active session already exists for: " + currentUser.getName() + " (@" + currentUser.getUsername() + ")");
            System.out.print("Do you want to (1) Open Dashboard, (2) Switch Account / Re-login, or (3) Return? (1/2/3): ");
            String option = scanner.nextLine().trim();
            if ("1".equals(option)) {
                displayUserDashboard();
                return true;
            } else if ("2".equals(option)) {
                handleLogout();
            } else {
                return true;
            }
        }

        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            ConsoleUtils.printError("Username and password cannot be empty.");
            return false;
        }

        try {
            User authenticatedUser = userService.authenticate(username, password);
            this.currentUser = authenticatedUser;

            ConsoleUtils.printSuccess("Login successful! Welcome back, " + authenticatedUser.getName() + "!");
            displayUserDashboard();
            return true;
        } catch (UserNotFoundException e) {
            ConsoleUtils.printError("Login Failed: " + e.getMessage());
            return false;
        } catch (AuthenticationException e) {
            ConsoleUtils.printError("Login Failed: " + e.getMessage());
            return false;
        } catch (InvalidUserDataException e) {
            ConsoleUtils.printError("Validation Error: " + e.getMessage());
            return false;
        } catch (SmartStockException e) {
            ConsoleUtils.printError("Authentication Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            ConsoleUtils.printError("Unexpected error during login: " + e.getMessage());
            return false;
        }
    }

    /**
     * Logs out the currently active session user.
     */
    public void handleLogout() {
        if (!isLoggedIn()) {
            ConsoleUtils.printInfo("No active user session is currently logged in.");
            return;
        }
        String userName = currentUser.getName();
        logout();
        ConsoleUtils.printSuccess("User '" + userName + "' has been logged out successfully.");
    }

    /**
     * Renders the interactive user dashboard for the authenticated user session.
     */
    public void displayUserDashboard() {
        if (!isLoggedIn()) {
            ConsoleUtils.printWarning("Please log in first to access the User Dashboard.");
            return;
        }

        boolean active = true;
        while (active) {
            System.out.println("\n==================================================");
            System.out.println("              USER DASHBOARD & SUMMARY            ");
            System.out.println("==================================================");
            System.out.println(" User ID:         " + currentUser.getUserId());
            System.out.println(" Full Name:       " + currentUser.getName());
            System.out.println(" Username:        @" + currentUser.getUsername());
            System.out.println(" Email:           " + currentUser.getEmail());
            System.out.println(" Virtual Balance: " + ConsoleUtils.formatCurrency(currentUser.getVirtualBalance()));
            System.out.println(" Status:          ACTIVE SESSION");
            System.out.println(" Member Since:    " + currentUser.getCreatedAt().format(DATE_FORMATTER));
            System.out.println("--------------------------------------------------");
            System.out.println(" 1. View Account Profile Details");
            System.out.println(" 2. View Virtual Balance Summary");
            System.out.println(" 3. Return to Main Menu (Keep Session Active)");
            System.out.println(" 4. Logout Session");
            System.out.println("==================================================");
            System.out.print("Select dashboard option (1-4): ");

            if (!scanner.hasNextLine()) {
                break;
            }
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displayUserProfile();
                    break;
                case "2":
                    displayBalanceSummary();
                    break;
                case "3":
                    ConsoleUtils.printInfo("Returning to Main Menu. Your session as '" + currentUser.getName() + "' remains active.");
                    active = false;
                    break;
                case "4":
                    handleLogout();
                    active = false;
                    break;
                default:
                    ConsoleUtils.printWarning("Invalid option '" + choice + "'. Please choose between 1 and 4.");
            }
        }
    }

    /**
     * Renders detailed user profile data.
     */
    public void displayUserProfile() {
        if (!isLoggedIn()) {
            ConsoleUtils.printWarning("No active user session.");
            return;
        }
        ConsoleUtils.printSectionHeader("User Profile Details");
        System.out.println("  User ID:         " + currentUser.getUserId());
        System.out.println("  Full Name:       " + currentUser.getName());
        System.out.println("  Username:        @" + currentUser.getUsername());
        System.out.println("  Email Address:   " + currentUser.getEmail());
        System.out.println("  Virtual Balance: " + ConsoleUtils.formatCurrency(currentUser.getVirtualBalance()));
        System.out.println("  Password Hash:   " + currentUser.getPasswordHash().substring(0, 16) + "... [SHA-256 Protected]");
        System.out.println("  Member Since:    " + currentUser.getCreatedAt().format(DATE_FORMATTER));
        ConsoleUtils.printDivider();
    }

    /**
     * Renders virtual cash balance information.
     */
    public void displayBalanceSummary() {
        if (!isLoggedIn()) {
            ConsoleUtils.printWarning("No active user session.");
            return;
        }
        ConsoleUtils.printSectionHeader("Virtual Wallet Summary");
        System.out.println("  Account Holder:  " + currentUser.getName());
        System.out.println("  Available Cash:  " + ConsoleUtils.formatCurrency(currentUser.getVirtualBalance()));
        System.out.println("  Currency:        USD ($)");
        System.out.println("  Trading Status:  Ready for simulation trading (Day 4)");
        ConsoleUtils.printDivider();
    }
}
