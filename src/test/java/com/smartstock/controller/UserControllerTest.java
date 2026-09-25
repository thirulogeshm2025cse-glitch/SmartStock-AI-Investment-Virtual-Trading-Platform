package com.smartstock.controller;

import com.smartstock.model.User;
import com.smartstock.repository.impl.InMemoryPortfolioRepository;
import com.smartstock.repository.impl.InMemoryUserRepository;
import com.smartstock.service.UserService;
import com.smartstock.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UserController interactions and session state tracking.
 */
class UserControllerTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(new InMemoryUserRepository(), new InMemoryPortfolioRepository());
    }

    @Test
    @DisplayName("Should register a user through UserController interactive flow")
    void testUserControllerRegister() {
        String simulatedInput = String.join(System.lineSeparator(),
                "USR-300",
                "Charlie Brown",
                "charlie@example.com",
                "charlieb",
                "pass1234",
                "5000.00"
        ) + System.lineSeparator();

        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(in);
        UserController userController = new UserController(userService, scanner);

        userController.handleRegister();

        // Verify user was registered in underlying service
        User registered = userService.getUserById("USR-300").orElse(null);
        assertNotNull(registered);
        assertEquals("Charlie Brown", registered.getName());
        assertEquals("charlieb", registered.getUsername());
        assertEquals(5000.0, registered.getVirtualBalance());
    }

    @Test
    @DisplayName("Should authenticate user and maintain active session through UserController")
    void testUserControllerLoginAndSession() {
        // Pre-register user
        userService.register("USR-400", "Diana Prince", "diana@example.com", "dianap", "amazon2026", 12000.0);

        // Login input: username, password, then choice "3" to return from dashboard
        String simulatedInput = String.join(System.lineSeparator(),
                "dianap",
                "amazon2026",
                "3" // return from dashboard to menu
        ) + System.lineSeparator();

        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(in);
        UserController userController = new UserController(userService, scanner);

        assertFalse(userController.isLoggedIn());
        assertNull(userController.getCurrentUser());

        boolean loginResult = userController.handleLogin();

        assertTrue(loginResult);
        assertTrue(userController.isLoggedIn());
        assertNotNull(userController.getCurrentUser());
        assertEquals("Diana Prince", userController.getCurrentUser().getName());
        assertEquals("USR-400", userController.getCurrentUser().getUserId());

        // Test logout
        userController.logout();
        assertFalse(userController.isLoggedIn());
        assertNull(userController.getCurrentUser());
    }

    @Test
    @DisplayName("Should reject invalid login credentials in UserController without crashing")
    void testUserControllerInvalidLogin() {
        userService.register("USR-500", "Clark Kent", "clark@example.com", "superman", "krypton1", 10000.0);

        String simulatedInput = String.join(System.lineSeparator(),
                "superman",
                "wrongPassword"
        ) + System.lineSeparator();

        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(in);
        UserController userController = new UserController(userService, scanner);

        boolean loginResult = userController.handleLogin();

        assertFalse(loginResult);
        assertFalse(userController.isLoggedIn());
        assertNull(userController.getCurrentUser());
    }
}
