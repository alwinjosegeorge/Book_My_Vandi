# 🚗 Book My Vandi – Car Rental Management System

A modern Java Swing-based desktop application for managing car rentals with integrated user authentication, admin management, and local database persistence using **SQLite**.  
Developed as part of an academic project under **Object-Oriented Programming (OOP)**, this system demonstrates complete CRUD functionality, modular MVC architecture, and a user-friendly graphical interface.

---

## 📋 Project Overview

**Book My Vandi** is a complete desktop solution for booking, managing, and monitoring car rentals.  
The application provides separate roles for **Admin**, **Seller**, and **User**, ensuring secure access and smooth coordination between all participants in the system.

---

## 🎯 Key Features

### 👥 User
- Create an account and log in securely.
- Search for available cars.
- Book cars for selected days.
- View booking history and confirmations.

### 🧑‍💼 Seller
- Add, edit, or remove car listings.
- View and confirm booking requests.
- Manage customer messages and booking details.

### 🛠️ Admin
- Manage all users and cars.
- View complete booking records.
- Oversee system activity and database integrity.

---

## 🧩 Architecture

📁 CarBookingSystem
├── 📁 src
│ ├── 📁 model # Data classes (Car, User, Booking)
│ ├── 📁 controller # Business logic & data flow
│ ├── 📁 view # GUI (Swing-based forms)
│ ├── 📁 dao # Data Access Objects for database CRUD
│ ├── 📁 database # Database connection classes
│ └── 📁 utils # Helper utilities for UI and formatting
├── 📁 resources
│ ├── 📁 images # App icons and images
│ └── 📁 screenshots # Output screenshots (UI pages)
└── 📁 bin # Compiled class files

---

## 🖼️ Application Screenshots

| Screen | Preview |
|--------|----------|
| **Main Login Page** | ![Main Login](resources/screenshots/MainLoginPage.png) |
| **Create Account** | ![Create Account](resources/screenshots/Create%20Account.png) |
| **OTP Verification** | ![OTP Page](resources/screenshots/OTP%20page.png) |
| **User Login** | ![User Login](resources/screenshots/User%20Login%20.png) |
| **User Dashboard** | ![User Interface](resources/screenshots/User%20Interface.png) |
| **Booking Demo** | ![Booking Demo](resources/screenshots/Booking%20Demo.png) |
| **Booking Confirmation** | ![Booking Confirmation](resources/screenshots/Booking%20Confirmation.png) |
| **Seller Login** | ![Seller Login](resources/screenshots/Seller%20Login%20Pages.png) |
| **Seller Confirmation** | ![Seller Confirmation](resources/screenshots/Seller%20Booking%20Conforming%20.png) |
| **Admin Login** | ![Admin Login](resources/screenshots/Admin%20Loggin.png) |
| **Admin Manage Cars** | ![Admin Manage Cars](resources/screenshots/Admin%20Manage%20Car.png) |
| **All Bookings (Admin)** | ![All Bookings](resources/screenshots/admin%20all%20booking.png) |

*(Add or rename screenshots as per your actual folder names.)*

---

## ⚙️ Tech Stack

- **Language:** Java (JDK 17+)
- **GUI Framework:** Swing
- **Database:** SQLite (via `sqlite-jdbc`)
- **Architecture:** MVC (Model–View–Controller)
- **IDE Recommended:** IntelliJ IDEA / Eclipse
- **Build Tool:** Manual compile or IDE build

---

## 🧱 Installation & Setup

### 1️⃣ Clone the repository
```bash
git clone https://github.com/alwinjosegeorge/Book_My_Vandi.git

This project follows the **MVC (Model–View–Controller)** design pattern to ensure separation of concerns and maintainable code.

