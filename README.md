# SmartStock – AI Investment & Virtual Trading Platform

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Build](https://img.shields.io/badge/Build-Maven-blue.svg)
![Architecture](https://img.shields.io/badge/Architecture-Layered-green.svg)
![Status](https://img.shields.io/badge/Status-Day%202%20Completed-brightgreen.svg)
![Tests](https://img.shields.io/badge/Tests-19%20Passed-success.svg)

## 📌 Project Overview
**SmartStock** is a modern, enterprise-structured Java virtual trading platform that empowers users to practice stock market trading in a risk-free simulation environment. The platform integrates virtual portfolio management, real-time market data models, and an AI-driven stock recommendation engine to help traders make informed investment decisions.

The project is engineered following clean Object-Oriented Programming (OOP) principles and a strict layered architecture to ensure scalability, testability, and maintainability across a structured 7-day development sprint.

---

## 🏛 System Architecture
SmartStock is designed with strict separation of concerns across four decoupled layers:
`Controller -> Service -> Repository -> Model`

```
src/main/java/com/smartstock/
│
├── model/           # Core domain entities with full encapsulation
│   ├── User.java                 # User profile, credentials, and virtual cash balance
│   ├── Stock.java                # Market asset details, pricing, and 24h metrics
│   ├── Holding.java              # User's stock position and cost basis
│   ├── Portfolio.java            # Aggregated holdings and total valuation
│   ├── Transaction.java          # Trade execution audit record
│   └── TransactionType.java      # BUY / SELL enumeration
│
├── repository/      # Data access layer contracts and persistence
│   ├── UserRepository.java       # User entity data access contract
│   ├── StockRepository.java      # Stock catalog query contract
│   ├── PortfolioRepository.java  # Portfolio state access contract
│   ├── TransactionRepository.java# Audit log storage contract
│   └── impl/
│       ├── InMemoryUserRepository.java       # Thread-safe user storage with indexes
│       ├── InMemoryStockRepository.java      # Pre-seeded market asset catalog
│       ├── InMemoryPortfolioRepository.java  # In-memory user-portfolio registry
│       └── InMemoryTransactionRepository.java# Append-only transaction ledger
│
├── service/         # Business logic layer with validation & authentication
│   ├── UserService.java          # User registration, authentication, & session contract
│   ├── StockService.java         # Market asset queries & filtering contract
│   ├── TradingService.java       # Order routing & trade execution contract
│   ├── PortfolioService.java     # Portfolio valuation & gain/loss contract
│   ├── PredictionService.java    # AI recommendation engine contract
│   └── impl/
│       ├── UserServiceImpl.java         # Registration, duplicate checks, SHA-256 auth
│       ├── StockServiceImpl.java        # Stock retrieval implementation
│       ├── TradingServiceImpl.java      # Trade execution engine (Day 4)
│       ├── PortfolioServiceImpl.java    # Portfolio metrics engine (Day 5)
│       └── PredictionServiceImpl.java   # AI trend prediction engine (Day 6)
│
├── controller/      # Presentation & user interaction layer
│   ├── ConsoleMenuController.java# Primary interactive application dashboard
│   └── UserController.java       # Registration, login, session state, & user dashboard
│
├── utility/         # Shared validators, security helpers, and UI formatters
│   ├── ConsoleUtils.java         # ANSI banners, dividers, and currency formatting
│   ├── InputValidator.java       # Email regex, username length, & numeric validation
│   └── PasswordUtils.java        # SHA-256 cryptographic password hashing & verification
│
├── exception/       # Domain-specific custom exception hierarchy
│   ├── SmartStockException.java          # Base unchecked application exception
│   ├── UserNotFoundException.java        # Thrown when username/ID does not exist
│   ├── UserAlreadyExistsException.java   # Thrown on duplicate username, email, or ID
│   ├── InvalidUserDataException.java     # Thrown on blank fields or invalid balance
│   ├── AuthenticationException.java      # Thrown on incorrect password credentials
│   ├── StockNotFoundException.java       # Thrown when ticker symbol is invalid
│   ├── InsufficientFundsException.java   # Thrown when balance is inadequate for buy
│   └── InsufficientSharesException.java  # Thrown when holding is inadequate for sell
│
└── Main.java        # Application bootstrap & dependency injection wiring
```

---

## ✨ Features Implemented (Day 2)

### 1. User Registration Flow
- Allows new users to specify:
  - **User ID** (e.g., `USR-101`)
  - **Full Name** (e.g., `John Doe`)
  - **Email Address** (validated against RFC email regex)
  - **Username** (minimum 3 characters)
  - **Password** (minimum 4 characters, hashed via SHA-256)
  - **Initial Virtual Balance** (validated positive amount, e.g. `$10,000.00`)
- Automatically provisions an empty virtual portfolio linked to the new user.

### 2. Comprehensive Field Validation & Duplicate Prevention
- **Empty Field Validation**: Rejects empty or whitespace-only inputs for all fields.
- **Duplicate ID Detection**: Prevents registering with an existing User ID.
- **Duplicate Username Detection**: Enforces unique usernames across the platform (case-insensitive).
- **Duplicate Email Detection**: Enforces unique emails across the platform (case-insensitive).
- **Balance Validation**: Rejects non-numeric, zero, or negative virtual balances.

### 3. Secure Authentication & Password Hashing
- Cryptographic **SHA-256 password hashing** (`PasswordUtils`) converts plain-text passwords into 64-character hexadecimal digests.
- Passwords are never stored or logged in plain-text.

### 4. Interactive User Login & Session Persistence
- Login authenticates credentials (`username` + `password`).
- Maintains the logged-in user in active application session memory (`UserController.getCurrentUser()`).
- Main menu dynamically displays active user name and real-time virtual cash balance.
- Session remains active across menu choices until explicitly logged out.

### 5. Dedicated User Dashboard & Profile View
- After successful login, users are greeted with their personalized dashboard:
  - Account Overview: User ID, Name, Username, Email, Cash Balance, Member Since timestamp.
  - Option to view full profile details (with protected hash preview).
  - Option to view virtual wallet summary.
  - Option to return to Main Menu while maintaining session state.
  - Option to safely log out.

---

## 🛠 Technologies Used
- **Language**: Java 21 (LTS)
- **Build Tool**: Apache Maven 3.9+
- **Paradigm**: Object-Oriented Programming (Encapsulation, Polymorphism, Abstraction, Clean Layering)
- **Collections**: `ConcurrentHashMap`, `HashMap`, `ArrayList`
- **Security**: Standard Java Cryptography Architecture (`java.security.MessageDigest` - SHA-256)
- **Testing**: JUnit 5 (Jupiter Engine) with 19 automated test cases

---

## 📅 7-Day Development Plan

| Day | Focus Area | Key Deliverables | Status |
|:---:|:---|:---|:---:|
| **Day 1** | **Project Setup & Architecture** | Project skeleton, layered architecture, domain models, exception hierarchy, in-memory repository interfaces, console navigation menu. | ✅ Completed |
| **Day 2** | **User Management & Authentication** | User registration flow, field validation, duplicate detection, SHA-256 hashing, login authentication, session management, user dashboard. | ✅ Completed |
| **Day 3** | **Market Data & Stock Service** | Extended stock catalog, dynamic price simulation / market feed mock, search and filter by sector. | ⏳ Planned |
| **Day 4** | **Virtual Trading Engine** | Buy/Sell order execution, funds validation, holding cost basis calculations, transaction record creation. | ⏳ Planned |
| **Day 5** | **Portfolio & Transaction History** | Live portfolio valuation, gain/loss metrics, holding breakdown, paginated transaction history. | ⏳ Planned |
| **Day 6** | **AI Stock Prediction & Analytics** | Trend prediction heuristics (moving averages, momentum indicators), AI recommendation signals. | ⏳ Planned |
| **Day 7** | **Integration, UI Polish & Final Demo** | End-to-end testing, error recovery polish, performance optimization, demo script, and final packaging. | ⏳ Planned |

---

## 🚀 How to Run the Project

### Prerequisites
- **Java Development Kit (JDK) 21** or later installed.
- **Apache Maven 3.8+** installed.

### Compile and Run
```bash
# Compile and package
mvn clean compile

# Run the interactive console application
mvn exec:java
```

### Run Automated Unit Tests
```bash
mvn test
```
All 19 test cases will execute across registration validation, duplicate checking, password hashing, authentication, and session handling.

---

## 🧭 How to Test Day 2 Features

### 1. Test User Registration
1. Launch the platform: `mvn exec:java`.
2. Select **`1`** (Register Account).
3. Enter test details:
   - User ID: `USR-101`
   - Full Name: `Alice Walker`
   - Email: `alice@example.com`
   - Username: `alicew`
   - Password: `password123`
   - Initial Virtual Balance: `25000.00`
4. Verify success message:
   ```
   [SUCCESS] User registration completed successfully!
   --------------------------------------------------
    User ID:         USR-101
    Full Name:       Alice Walker
    Username:        @alicew
    Email:           alice@example.com
    Virtual Balance: $25,000.00
    Registration:    2026-09-25 09:30:00
   --------------------------------------------------
   ```
5. Test validation: Try registering another user with:
   - Duplicate username `alicew` -> `[ERROR] Duplicate Account: Username 'alicew' is already taken.`
   - Invalid email `invalid-email` -> `[ERROR] Invalid Registration Data: Invalid email format.`
   - Negative balance `-500` -> `[ERROR] Invalid Registration Data: Initial virtual balance must be greater than zero.`

### 2. Test User Login & Dashboard
1. Select **`2`** (Login).
2. Enter:
   - Username: `alicew`
   - Password: `password123`
3. Verify welcome message and User Dashboard display:
   ```
   [SUCCESS] Login successful! Welcome back, Alice Walker!

   ==================================================
                 USER DASHBOARD & SUMMARY            
   ==================================================
    User ID:         USR-101
    Full Name:       Alice Walker
    Username:        @alicew
    Email:           alice@example.com
    Virtual Balance: $25,000.00
    Status:          ACTIVE SESSION
   --------------------------------------------------
    1. View Account Profile Details
    2. View Virtual Balance Summary
    3. Return to Main Menu (Keep Session Active)
    4. Logout Session
   ==================================================
   ```
4. Select **`3`** to return to the Main Menu. Notice the header:
   ```
   ==================================================
                 SMARTSTOCK MAIN MENU                
    [Active Session: Alice Walker (@alicew) | Balance: $25,000.00]
   ==================================================
   ```
5. Navigate through options while session remains maintained.
6. Return to Option 2 or choose Logout to cleanly terminate the session.

