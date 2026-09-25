package com.smartstock.service;

import com.smartstock.exception.AuthenticationException;
import com.smartstock.exception.InvalidUserDataException;
import com.smartstock.exception.UserAlreadyExistsException;
import com.smartstock.exception.UserNotFoundException;
import com.smartstock.model.User;
import com.smartstock.repository.PortfolioRepository;
import com.smartstock.repository.UserRepository;
import com.smartstock.repository.impl.InMemoryPortfolioRepository;
import com.smartstock.repository.impl.InMemoryUserRepository;
import com.smartstock.service.impl.UserServiceImpl;
import com.smartstock.utility.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive JUnit 5 test suite for Day 2 UserService registration and authentication logic.
 */
class UserServiceTest {

    private UserRepository userRepository;
    private PortfolioRepository portfolioRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        portfolioRepository = new InMemoryPortfolioRepository();
        userService = new UserServiceImpl(userRepository, portfolioRepository);
    }

    @Nested
    @DisplayName("User Registration Tests")
    class RegistrationTests {

        @Test
        @DisplayName("Should successfully register a new user with valid details")
        void testSuccessfulRegistration() {
            User user = userService.register(
                    "USR-100",
                    "Alice Walker",
                    "alice@example.com",
                    "alicew",
                    "secret123",
                    15000.0
            );

            assertNotNull(user);
            assertEquals("USR-100", user.getUserId());
            assertEquals("Alice Walker", user.getName());
            assertEquals("alice@example.com", user.getEmail());
            assertEquals("alicew", user.getUsername());
            assertEquals(15000.0, user.getVirtualBalance());
            assertTrue(PasswordUtils.verifyPassword("secret123", user.getPasswordHash()));
            assertNotEquals("secret123", user.getPasswordHash(), "Password should be hashed");

            // Verify portfolio was initialized
            assertTrue(portfolioRepository.existsByUserId("USR-100"));
        }

        @Test
        @DisplayName("Should fail registration when User ID is empty")
        void testEmptyUserId() {
            assertThrows(InvalidUserDataException.class, () ->
                    userService.register("", "Alice", "alice@example.com", "alice", "pass123", 1000.0)
            );
        }

        @Test
        @DisplayName("Should fail registration when Name is blank")
        void testBlankName() {
            assertThrows(InvalidUserDataException.class, () ->
                    userService.register("USR-101", "   ", "alice@example.com", "alice", "pass123", 1000.0)
            );
        }

        @Test
        @DisplayName("Should fail registration when Email is empty")
        void testEmptyEmail() {
            assertThrows(InvalidUserDataException.class, () ->
                    userService.register("USR-101", "Alice", "", "alice", "pass123", 1000.0)
            );
        }

        @Test
        @DisplayName("Should fail registration when Email format is invalid")
        void testInvalidEmailFormat() {
            assertThrows(InvalidUserDataException.class, () ->
                    userService.register("USR-101", "Alice", "not-an-email", "alice", "pass123", 1000.0)
            );
        }

        @Test
        @DisplayName("Should fail registration when Username is too short (< 3 chars)")
        void testShortUsername() {
            assertThrows(InvalidUserDataException.class, () ->
                    userService.register("USR-101", "Alice", "alice@example.com", "al", "pass123", 1000.0)
            );
        }

        @Test
        @DisplayName("Should fail registration when Password is too short (< 4 chars)")
        void testShortPassword() {
            assertThrows(InvalidUserDataException.class, () ->
                    userService.register("USR-101", "Alice", "alice@example.com", "alice", "123", 1000.0)
            );
        }

        @Test
        @DisplayName("Should fail registration when initial balance is zero or negative")
        void testInvalidInitialBalance() {
            assertThrows(InvalidUserDataException.class, () ->
                    userService.register("USR-101", "Alice", "alice@example.com", "alice", "pass123", 0.0)
            );
            assertThrows(InvalidUserDataException.class, () ->
                    userService.register("USR-102", "Bob", "bob@example.com", "bobbb", "pass123", -500.0)
            );
        }

        @Test
        @DisplayName("Should fail registration when User ID is duplicate")
        void testDuplicateUserId() {
            userService.register("USR-101", "Alice", "alice@example.com", "alice1", "pass123", 1000.0);

            UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class, () ->
                    userService.register("USR-101", "Another Alice", "another@example.com", "alice2", "pass123", 2000.0)
            );
            assertTrue(ex.getMessage().contains("User ID"));
        }

        @Test
        @DisplayName("Should fail registration when Username is duplicate (case-insensitive)")
        void testDuplicateUsername() {
            userService.register("USR-101", "Alice", "alice@example.com", "ALICE", "pass123", 1000.0);

            UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class, () ->
                    userService.register("USR-102", "Bob", "bob@example.com", "alice", "pass123", 2000.0)
            );
            assertTrue(ex.getMessage().contains("Username"));
        }

        @Test
        @DisplayName("Should fail registration when Email is duplicate (case-insensitive)")
        void testDuplicateEmail() {
            userService.register("USR-101", "Alice", "alice@example.com", "alice", "pass123", 1000.0);

            UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class, () ->
                    userService.register("USR-102", "Bob", "ALICE@EXAMPLE.COM", "bob123", "pass123", 2000.0)
            );
            assertTrue(ex.getMessage().contains("Email"));
        }
    }

    @Nested
    @DisplayName("User Authentication and Login Tests")
    class AuthenticationTests {

        @BeforeEach
        void registerInitialUser() {
            userService.register(
                    "USR-200",
                    "Bob Smith",
                    "bob@example.com",
                    "bobsmith",
                    "Tr@der2026",
                    25000.0
            );
        }

        @Test
        @DisplayName("Should authenticate successfully with correct username and password")
        void testSuccessfulLogin() {
            User user = userService.authenticate("bobsmith", "Tr@der2026");
            assertNotNull(user);
            assertEquals("USR-200", user.getUserId());
            assertEquals("Bob Smith", user.getName());
            assertEquals(25000.0, user.getVirtualBalance());

            Optional<User> optionalUser = userService.login("bobsmith", "Tr@der2026");
            assertTrue(optionalUser.isPresent());
            assertEquals("USR-200", optionalUser.get().getUserId());
        }

        @Test
        @DisplayName("Should fail authentication with wrong password")
        void testIncorrectPassword() {
            AuthenticationException ex = assertThrows(AuthenticationException.class, () ->
                    userService.authenticate("bobsmith", "wrongPassword")
            );
            assertTrue(ex.getMessage().contains("Invalid password"));

            Optional<User> optionalUser = userService.login("bobsmith", "wrongPassword");
            assertTrue(optionalUser.isEmpty());
        }

        @Test
        @DisplayName("Should fail authentication when user does not exist")
        void testNonExistentUser() {
            UserNotFoundException ex = assertThrows(UserNotFoundException.class, () ->
                    userService.authenticate("ghostUser", "anyPassword")
            );
            assertTrue(ex.getMessage().contains("not found"));

            Optional<User> optionalUser = userService.login("ghostUser", "anyPassword");
            assertTrue(optionalUser.isEmpty());
        }

        @Test
        @DisplayName("Should fail authentication when fields are blank")
        void testBlankLoginFields() {
            assertThrows(InvalidUserDataException.class, () ->
                    userService.authenticate("", "password")
            );
            assertThrows(InvalidUserDataException.class, () ->
                    userService.authenticate("bobsmith", "   ")
            );
        }
    }

    @Nested
    @DisplayName("Password Utility Tests")
    class PasswordUtilTests {

        @Test
        @DisplayName("Should correctly hash and verify passwords using SHA-256")
        void testPasswordHashing() {
            String rawPassword = "SecureTradingPassword#1";
            String hash = PasswordUtils.hashPassword(rawPassword);

            assertNotNull(hash);
            assertEquals(64, hash.length(), "SHA-256 hex string should be 64 characters");
            assertTrue(PasswordUtils.verifyPassword(rawPassword, hash));
            assertFalse(PasswordUtils.verifyPassword("WrongPassword", hash));
        }
    }
}
