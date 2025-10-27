# 🚗 Book My Vandi

A modern **Car Rental Management System** designed to simplify vehicle booking, approval, and management — built with **Java Swing** and **SQLite** for fast, local data handling.

![Account Creation Confirmation](Screenshot/Account%20Creation%20Confirmation.png)

---

## 📖 Overview

**Book My Vandi** is a desktop-based **Car Rental Management System (CRMS)** that allows users to register, log in, browse available vehicles, make bookings, and manage rental approvals with ease.

The project follows a structured **MVC architecture** ensuring scalability, maintainability, and readability.  
It provides a professional, responsive user interface powered by **Java Swing**, while **SQLite** ensures reliable, offline data persistence.

---

## ✨ Key Features

- 🔐 **User Authentication** – Secure registration and login for all users.
- 🚘 **Vehicle Management** – Add, edit, and manage car details efficiently.
- 📅 **Booking System** – Rent cars with a user-friendly booking interface.
- ✅ **Admin Approval Workflow** – Admins can approve or reject bookings.
- 📊 **Dashboard View** – Display of user statistics and booking history.
- 💾 **SQLite Database** – Lightweight, local database storage.
- 🧩 **MVC Structure** – Clean separation of Model, View, and Controller layers.

---

## 🏗️ Tech Stack

| Layer | Technology |
|:------|:------------|
| **Frontend (UI)** | Java Swing |
| **Backend (Logic)** | Java (OOP Concepts) |
| **Database** | SQLite |
| **Architecture** | MVC (Model–View–Controller) |


## 📁 Project Structure


```
📁 CarBookingSystem
├── 📁 src
│ ├── 📁 model # Data models: Car, User, Bookings
│ ├── 📁 controller # Logic for user, car, and booking management
│ ├── 📁 view # GUI (Swing Forms)
│ ├── 📁 dao # Database operations
│ ├── 📁 database # SQLite connection handling
│ └── 📁 utils # Helper utilities
├── 📁 Screenshot # Application output screenshots
│ ├── Account Creation Confirmation.png
│ ├── Admin Loggin.png
│ ├── Booking Confirmation.png
│ ├── Booking Demo.png
│ ├── Create Account.png
│ ├── OTP page.png
│ ├── Seller Booking Conforming .png
│ ├── User Interface.png
│ ├── User Login .png
│ └── ... (more screenshots)
└── 📁 bin # Compiled .class files
```

---

## ⚙️ Installation & Setup

1. **Clone this repository:**
   ```bash
   git clone https://github.com/alwinjosegeorge/Book_My_Vandi.git
   cd Book_My_Vandi
Add the SQLite JDBC Driver:
Download sqlite-jdbc-<version>.jar and place it in your project directory.

Compile and Run:

javac -cp .;sqlite-jdbc-<version>.jar Main.java
java -cp .;sqlite-jdbc-<version>.jar Main


Login or Create an Account to start using the system.

📸 Screenshots
Feature	Preview
Account Creation Confirmation	
🚀 Future Enhancements

🌐 Cloud database integration

📱 Mobile-friendly UI using JavaFX or React Native

🔔 Email/SMS booking notifications

🧾 Payment gateway integration

🧠 AI-based vehicle recommendation system

👨‍💻 Team Members
Member	GitHub Username	Role
Alwin Jose George	@alwinjosegeorge
	Project Lead / Developer
Joe Martin Rince	@JoeMartinRince
	Backend Developer
Alex Roy	@AlexRoy2006
	UI & Database Design
Febin Nobel	@febin-04
	Testing & Documentation
📜 License

This project is licensed under the MIT License.

---


