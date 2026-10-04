# 🏦 CodSoft Java Development Internship Tasks

[![Java](https://img.shields.io/badge/Java-17+-orange?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![CodSoft](https://img.shields.io/badge/Internship-CodSoft-blue)](https://www.codsoft.in)

> **Java Development Virtual Internship** - Completed 3 core tasks demonstrating Java fundamentals, OOP concepts, and console-based application development.

---

## 📋 Tasks Overview

| Task | Name | Description | Concepts Covered |
|------|------|-------------|------------------|
| **1** | 🎮 **Number Game** | Random number guessing game with attempts, rounds & scoring | Loops, Random, Scanner, Input validation, Game logic |
| **2** | 📊 **Student Grade Calculator Pro** | Multi-student grade management with GPA, statistics & CSV persistence | OOP, Collections, File I/O, CSV, Streams, Menu-driven UI |
| **3** | 🏧 **ATM Interface** | Full-featured banking system with transactions, statements & security | OOP Design, Encapsulation, Maps, Exception handling, State management |

---

## 🚀 Quick Start

### Prerequisites
- Java 17+ (or Java 11+)
- Git (optional)

### Run Locally

```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/CODSOFT_TASKS.git
cd CODSOFT_TASKS

# Compile & Run Task 1 - Number Game
cd Task1_NumberGame/src
javac NumberGame.java
java NumberGame

# Compile & Run Task 2 - Grade Calculator
cd ../../Task2_GradeCalculator/src
javac GradeCalculatorPro.java
java GradeCalculatorPro

# Compile & Run Task 3 - ATM Interface
cd ../../Task3_ATMInterface/src
javac *.java
java Main
Using IntelliJ IDEA (Recommended)
1. File → Open → Select CODSOFT_TASKS folder
2. Right-click any Main class → Run
🎮 Task 1: Number Game
Features:
- Random number generation (1-100)
- Limited attempts (7 per round)
- Multiple rounds with score tracking
- High/Low feedback
- Input validation
- Final statistics (win rate, total score)
Sample Run:
🎮 WELCOME TO NUMBER GUESSING GAME 🎮
Range: 1 to 100 | Max attempts: 7

--- Round 1 ---
Guess the number between 1 and 100
Attempt 1/7: 50
📉 Too HIGH! Try a lower number.
Attempt 2/7: 25
📈 Too LOW! Try a higher number.
...
✅ Correct! You guessed it in 3 attempts!
🏆 Round Score: 50 | Total Score: 50

🔄 Play another round? (y/n): y
📊 Task 2: Student Grade Calculator Pro
Features:
- Multi-student management - Add unlimited students with roll numbers
- Persistent storage - Auto-saves to students_data.csv
- Subject-wise grades - Individual grade per subject
- GPA calculation - 10-point scale (A+=10, A=9, B+=8...)
- Class statistics - Average, highest, lowest, pass rate
- Subject analysis - Per-subject avg, max, min, pass%
- Grade distribution - Visual bar chart
- Export reports - Individual report cards + class summary
- Search - By name or roll number
- Professional menu-driven UI
Sample Run:
======================================
   STUDENT GRADE CALCULATOR PRO
======================================
1. ➕ Add New Student
2. 📋 View All Students
3. 🔍 Search Student
4. 📊 View Statistics
5. 💾 Save & Export
6. ⚙️  Settings
0. 🚪 Exit
Choose option: 1

Student Name: Rahul Sharma
Roll Number: CS2024001
Number of subjects (1-15): 3

--- Subject 1 ---
Subject Name: Mathematics
Marks (0-100): 95

--- Subject 2 ---
Subject Name: Physics
Marks (0-100): 88
...
✅ Student added successfully!

📋 REPORT CARD: Rahul Sharma (CS2024001)
Subject              Marks Grade
----------------------------------
Mathematics           95/100   A+
Physics               88/100   A
Chemistry             92/100   A+
----------------------------------
TOTAL                275/300
AVERAGE              91.67%
GRADE                A+
STATUS               ✅ PASS
GPA                  9.67
🏧 Task 3: ATM Interface
Features:
- Multi-account support - 3 pre-loaded test accounts
- PIN authentication - 3 attempts lockout
- Balance inquiry - Masked account display
- Cash deposit - ₹50K limit with note counting animation
- Cash withdrawal - ₹10K/transaction, ₹25K daily, ₹1K min balance
- Mini statement - Last 5 transactions
- Full statement - Complete history with descriptions
- PIN change - Validation + confirmation
- Transaction receipts - Auto-printed after each transaction
- Session memory - Remembers last logged-in account
- Account lookup by name - No need to memorize account numbers
Test Accounts:
Account No.	Name	PIN	Balance
1234567890	Rahul Sharma	1234	₹50,000
9876543210	Priya Singh	4321	₹75,000
1111222233	Amit Kumar	1111	₹25,000
Sample Run:
╔══════════════════════════════════════╗
║     🏦 WELCOME TO CODSOFT ATM       ║
╚══════════════════════════════════════╝

--- LOGIN ---
1. Account Number
2. Search by Name
Choose: 2
Enter Account Holder Name: rahul
✅ Found: Rahul Sharma (**** 7890)
Enter 4-digit PIN: 1234
✅ Login successful! Welcome, Rahul Sharma

╔══════════════════════════════════════╗
║         MAIN MENU                   ║
╠══════════════════════════════════════╣
║  1. 💰 Check Balance                ║
║  2. 💵 Deposit Cash                 ║
║  3. 🏧 Withdraw Cash                ║
║  4. 📄 Mini Statement (Last 5)      ║
║  5. 📋 Full Statement               ║
║  6. 🔐 Change PIN                   ║
║  7. 🚪 Logout                       ║
║  0. ⛔ Exit ATM                     ║
╚══════════════════════════════════════╝
Select option: 3

🏧 CASH WITHDRAWAL
Available Balance: ₹50,000.00
Daily Limit: ₹25,000 | Per Transaction: ₹10,000
Enter amount to withdraw: ₹5000

⠋ Processing...
✅ Withdrawal successful. New balance: ₹45,000.00

─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─
       CODSOFT ATM - RECEIPT
─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─
Transaction: WITHDRAWAL
Account: **** **** **** 7890
Name: Rahul Sharma
Amount: ₹5000.00
Balance: ₹45,000.00
Date: 04-10-2026 14:30:25
─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─
   Thank you for banking with us!
─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─━─
📁 Project Structure
CODSOFT_TASKS/
├── README.md                    # This file
├── LICENSE                      # MIT License
├── .gitignore                   # Java gitignore
├── Task1_NumberGame/
│   ├── src/
│   │   └── NumberGame.java      # Main game class
│   └── README.md                # Task-specific docs
├── Task2_GradeCalculator/
│   ├── src/
│   │   └── GradeCalculatorPro.java  # Enhanced calculator
│   ├── students_data.csv        # Auto-generated data file
│   └── README.md
└── Task3_ATMInterface/
    ├── src/
    │   ├── Main.java            # Entry point
    │   ├── ATM.java             # ATM service layer
    │   └── BankAccount.java     # Account model + transactions
    └── README.md
🛠️ Technical Highlights
Task 1 - Core Java
- java.util.Random for number generation
- Scanner for input handling
- Exception handling with try-catch
- Game loop with state management
Task 2 - OOP & Data Persistence
- Encapsulation: Student and Subject inner classes
- Collections: ArrayList, HashMap, LinkedHashMap
- Streams API: filter, map, collect, groupingBy
- File I/O: BufferedReader, PrintWriter, CSV parsing
- Date/Time: LocalDateTime, DateTimeFormatter
- CSV Handling: Custom parser with quote support
Task 3 - System Design
- Layered Architecture: Model (BankAccount) → Service (ATM) → UI (Main)
- Immutable Fields: final for account number, name, PIN
- Result Objects: WithdrawResult for typed responses
- State Management: Daily withdrawal limit tracking
- Transaction Logging: Timestamped history with descriptions
📸 Demo Videos
Required for submission - Post on LinkedIn with #codsoft
Task	Video Link
Number Game	[Add your LinkedIn video URL]
Grade Calculator	[Add your LinkedIn video URL]
ATM Interface	[Add your LinkedIn video URL]
📚 Learning Outcomes
- ✅ Java syntax, control structures, methods
- ✅ Object-Oriented Programming (Encapsulation, Abstraction)
- ✅ Collections Framework (List, Map, Stream API)
- ✅ Exception Handling & Input Validation
- ✅ File I/O & Data Persistence (CSV)
- ✅ Date/Time API (java.time)
- ✅ Console UI/UX Design
- ✅ Git & GitHub workflow
- ✅ Code organization & documentation
🤝 Internship Info
Organization: CodSoft  
Program: Java Development Virtual Internship  
Duration: 4 Weeks  
Tasks Required: Minimum 3 of 5  
Status: ✅ Completed 3 Tasks
📄 License
This project is licensed under the MIT License - see the LICENSE file for details.
🙏 Acknowledgments
- CodSoft for the internship opportunity
- Java community for excellent documentation
- All open-source resources used for reference
<div align="center">
Made with ☕ and 💻 during CodSoft Java Development Internship
LinkedIn
GitHub
</div>
```
Task-Specific READMEs
Task1_NumberGame/README.md
# Task 1: Number Guessing Game

## Description
A console-based number guessing game where the player tries to guess a randomly generated number between 1-100 within limited attempts.

## Features
- Random number generation (1-100)
- 7 attempts per round
- Multiple rounds support
- Score tracking (fewer attempts = higher score)
- High/Low hints
- Input validation
- Final statistics (rounds played, won, win rate, total score)

## How to Run
```bash
cd src
javac NumberGame.java
java NumberGame
Concepts Used
- java.util.Random
- Scanner for input
- while loops & game state
- Exception handling (NumberFormatException)
- Methods for code organization

---

### **Task2_GradeCalculator/README.md**
```markdown
# Task 2: Student Grade Calculator Pro

## Description
An enhanced grade management system supporting multiple students, persistent storage, statistics, and report generation.

## Features
- Multi-student management with roll numbers
- Auto-save/load from CSV (`students_data.csv`)
- Subject-wise grading
- GPA calculation (10-point scale)
- Class statistics & subject analysis
- Grade distribution visualization
- Export individual report cards & class summary
- Search by name/roll number
- Menu-driven interface

## How to Run
```bash
cd src
javac GradeCalculatorPro.java
java GradeCalculatorPro
Data Persistence
- Automatically saves to students_data.csv on exit
- Loads existing data on startup
- Export options: Individual .txt reports or class summary
Concepts Used
- OOP: Encapsulation, Inner classes
- Collections: List, Map, Stream API
- File I/O: BufferedReader, PrintWriter
- CSV parsing with quote support
- java.time API for timestamps

---

### **Task3_ATMInterface/README.md**
```markdown
# Task 3: ATM Interface

## Description
A full-featured console-based ATM simulation with multi-account support, transaction history, and banking operations.

## Features
- 3 pre-loaded test accounts
- PIN authentication (3 attempts)
- Balance inquiry
- Cash deposit (₹50K max)
- Cash withdrawal (₹10K/txn, ₹25K/day, ₹1K min balance)
- Mini statement (last 5 transactions)
- Full statement (complete history)
- PIN change with validation
- Transaction receipts
- Session memory (remembers last account)
- Account lookup by name

## Test Accounts
| Account | Name | PIN | Balance |
|---------|------|-----|---------|
| 1234567890 | Rahul Sharma | 1234 | ₹50,000 |
| 9876543210 | Priya Singh | 4321 | ₹75,000 |
| 1111222233 | Amit Kumar | 1111 | ₹25,000 |

## How to Run
```bash
cd src
javac *.java
java Main
Architecture
Main.java → ATM.java (Service) → BankAccount.java (Model)
                    ↓
            Transaction logging
            State management
Concepts Used
- Layered architecture (Model-Service-UI)
- Encapsulation with private final fields
- Map<String, BankAccount> for account storage
- Custom result objects (WithdrawResult)
- Daily limit tracking with LocalDate
- Transaction history with timestamps