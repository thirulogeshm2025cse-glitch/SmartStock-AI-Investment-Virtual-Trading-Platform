# SmartStock – AI Investment & Virtual Trading Platform

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Build](https://img.shields.io/badge/Build-Maven-blue.svg)
![Architecture](https://img.shields.io/badge/Architecture-Layered-green.svg)
![Status](https://img.shields.io/badge/Status-Day%201%20Completed-brightgreen.svg)

## 📌 Project Overview
**SmartStock** is a modern, enterprise-structured Java virtual trading platform that empowers users to practice stock market trading in a risk-free simulation environment. The platform integrates virtual portfolio management, real-time market data models, and an AI-driven stock recommendation engine to help traders make informed investment decisions.

The project is engineered following clean Object-Oriented Programming (OOP) principles and a strict layered architecture to ensure scalability, testability, and maintainability across a structured 7-day development sprint.

---

## 🏛 System Architecture
SmartStock is designed with separation of concerns across distinct layers:

```
src/main/java/com/smartstock/
│
├── model/           # Core domain entities with full encapsulation
│   ├── User.java
│   ├── Stock.java
│   ├── Holding.java
│   ├── Portfolio.java
│   ├── Transaction.java
│   └── TransactionType.java
│
├── repository/      # Data access layer contracts and in-memory persistence
│   ├── UserRepository.java
│   ├── StockRepository.java
│   ├── PortfolioRepository.java
│   ├── TransactionRepository.java
│   └── impl/
│       ├── InMemoryUserRepository.java
│       ├── InMemoryStockRepository.java
│       ├── InMemoryPortfolioRepository.java
│       └── InMemoryTransactionRepository.java
│
├── service/         # Business logic layer with clean contracts
│   ├── UserService.java
│   ├── StockService.java
│   ├── TradingService.java
│   ├── PortfolioService.java
│   ├── PredictionService.java
│   └── impl/
│       ├── UserServiceImpl.java
│       ├── StockServiceImpl.java
│       ├── TradingServiceImpl.java
│       ├── PortfolioServiceImpl.java
│       └── PredictionServiceImpl.java
│
├── controller/      # User interaction and console presentation flow
│   └── ConsoleMenuController.java
│
├── utility/         # Shared validators, console decorators, and helpers
│   ├── ConsoleUtils.java
│   └── InputValidator.java
│
├── exception/       # Domain-specific custom exceptions
│   ├── SmartStockException.java
│   ├── UserNotFoundException.java
│   ├── StockNotFoundException.java
│   ├── InsufficientFundsException.java
│   └── InsufficientSharesException.java
│
└── Main.java        # Application bootstrap & dependency injection wiring
```

---

## ✨ Features Planned
- **Authentication & User Management**: Secure user registration, authentication, and virtual balance provisioning ($10,000 default balance).
- **Market Data Engine**: Live ticker discovery, sector-based categorization, and daily price range tracking.
- **Virtual Trading Engine**: Buy and sell order routing, balance checks, holding updates, and trade validation.
- **Portfolio Tracking**: Real-time unrealized P&L calculations, cost basis averaging, and asset allocation breakdown.
- **Transaction Audit Trail**: Immutable transaction logging with timestamps, share prices, and order types.
- **AI Stock Prediction**: Algorithmic technical trend analysis, sentiment indicators, and automated Buy/Hold/Sell signals.

---

## 🛠 Technologies Used
- **Language**: Java 21 (LTS)
- **Build Tool**: Apache Maven 3.9+
- **Paradigm**: Object-Oriented Programming (Encapsulation, Polymorphism, Abstraction, Interface Segregation)
- **Persistence**: Thread-safe In-Memory Collections (`ConcurrentHashMap`) with clear Repository interfaces (extensible to JDBC/JPA)
- **Testing**: JUnit 5

---

## 📅 7-Day Development Plan

| Day | Focus Area | Key Deliverables | Status |
|:---:|:---|:---|:---:|
| **Day 1** | **Project Setup & Architecture** | Project skeleton, layered architecture, domain models, exception hierarchy, in-memory repository interfaces, console navigation menu. | ✅ Completed |
| **Day 2** | **User Management & Authentication** | User registration flow, credential validation, login session handling, virtual wallet funding. | ⏳ Planned |
| **Day 3** | **Market Data & Stock Service** | Extended stock catalog, dynamic price simulation / market feed mock, search and filter by sector. | ⏳ Planned |
| **Day 4** | **Virtual Trading Engine** | Buy/Sell order execution, funds validation, holding cost basis calculations, transaction record creation. | ⏳ Planned |
| **Day 5** | **Portfolio & Transaction History** | Live portfolio valuation, gain/loss metrics, holding breakdown, paginated transaction history. | ⏳ Planned |
| **Day 6** | **AI Stock Prediction & Analytics** | Trend prediction heuristics (moving averages, momentum indicators), AI recommendation signals. | ⏳ Planned |
| **Day 7** | **Integration, UI Polish & Final Demo** | End-to-end testing, error recovery polish, performance optimization, demo script, and final packaging. | ⏳ Planned |

---

## 🚀 How to Run the Project

### Prerequisites
- **Java Development Kit (JDK) 21** or later installed.
- **Apache Maven 3.8+** installed (optional, direct `javac` also supported).

### Option 1: Using Apache Maven (Recommended)
1. **Compile the project**:
   ```bash
   mvn clean compile
   ```
2. **Run the application**:
   ```bash
   mvn exec:java
   ```

### Option 2: Using Standard JDK (`javac` & `java`)
From the project root directory:

#### On Windows (PowerShell):
```powershell
# Create output directory
New-Item -ItemType Directory -Force -Path bin

# Compile all source files
javac -d bin (Get-ChildItem -Recurse -Filter *.java src/main/java | Select-Object -ExpandProperty FullName)

# Run Main
java -cp bin com.smartstock.Main
```

#### On Linux / macOS (Bash):
```bash
# Create output directory
mkdir -p bin

# Compile all source files
find src/main/java -name "*.java" > sources.txt
javac -d bin @sources.txt
rm sources.txt

# Run Main
java -cp bin com.smartstock.Main
```

---

## 🧭 Day 1 Console Menu Navigation
When launched, SmartStock presents an interactive 9-option dashboard:
```
========== SMARTSTOCK MAIN MENU ==========
 1. Register
 2. Login
 3. View Stocks
 4. Buy Stock
 5. Sell Stock
 6. View Portfolio
 7. View Transactions
 8. AI Prediction
 9. Exit
==========================================
```
- **Option 3** directly queries the seeded repository through the layered architecture to display active stocks (AAPL, MSFT, GOOGL, AMZN, TSLA, NVDA).
- **Options 1, 2, 4, 5, 6, 7, 8** route through the appropriate layer controllers and indicate the scheduled development day.
- **Option 9** safely terminates the application.
