# 🚗 Book My Vandi

### A Smart Car Rental Management System Built with Java Swing + SQLite

![Book My Vandi Banner](Screenshot/Account%20Creation%20Confirmation.png)

---

## 🧩 Overview

**Book My Vandi** is a desktop-based car rental management system that simplifies vehicle booking, approval, and tracking.  
It’s designed with a professional MVC structure using **Java Swing** for the frontend and **SQLite** for persistent local data storage.  

This project ensures smooth management of customers, cars, and bookings — ideal for small rental agencies and college project demonstrations.

---

## ✨ Key Features

- 🔐 **User Authentication** – Secure login and account creation  
- 🚘 **Car Management** – Add, edit, and remove car details easily  
- 📅 **Booking System** – Book and track rentals with instant confirmation  
- 👥 **Admin Panel** – Approve or reject bookings  
- 💾 **SQLite Integration** – Local database for offline persistence  
- 🧾 **Detailed Records** – Manage bookings, returns, and customers  
- 💡 **Responsive UI** – Built using Java Swing with modern design principles  

---

## 🛠️ Tech Stack

| Category | Technology |
|-----------|-------------|
| Language | Java (JDK 17+) |
| GUI | Java Swing |
| Database | SQLite |
| Library | SQLite JDBC Driver |
| Architecture | MVC (Model-View-Controller) |

---

## 📁 Project Structure

```
Book_My_Vandi/
│
├── src/
│   ├── controller/     # Application logic and event handling
│   ├── database/       # SQLite DB connection handler
│   ├── model/          # Data models (User, Car, Booking)
│   ├── view/           # UI forms and dashboards
│   └── MainFrame.java  # Main application entry point
│
├── Screenshot/         # Screenshots for documentation
│
├── sqlite-jdbc.jar     # SQLite JDBC Driver
└── README.md
```

---

## ⚙️ Installation & Setup

1️⃣ **Clone the repository**
```bash
git clone https://github.com/alwinjosegeorge/Book_My_Vandi.git
cd Book_My_Vandi
```

2️⃣ **Add the SQLite JDBC Driver**
- Download the required version of `sqlite-jdbc.jar`  
- Place it inside your project root directory.

3️⃣ **Compile the project**
```bash
javac -cp ".;sqlite-jdbc.jar" src/*.java
```

4️⃣ **Run the application**
```bash
java -cp ".;sqlite-jdbc.jar" src.MainFrame
```

🧩 You can also open the project directly in **IntelliJ IDEA** or **Eclipse** and run the main file.

---

## 🚀 Future Enhancements

- 🌐 Online database connectivity (MySQL / Firebase)  
- 💳 Integrated payment gateway  
- 📱 Mobile-friendly UI using JavaFX or React Native  
- 🔔 Email / SMS booking notifications  
- 🧠 AI-based car recommendation and pricing engine  

---

## 👨‍💻 Team Members

| Member | GitHub | Role |
|---------|---------|------|
| **Alwin Jose George** | [@alwinjosegeorge](https://github.com/alwinjosegeorge) | Project Lead / Full Stack Developer |
| **Joe Martin Rince** | [@JoeMartinRince](https://github.com/JoeMartinRince) | Backend Developer |
| **Alex Roy** | [@AlexRoy2006](https://github.com/AlexRoy2006) | UI & Database Design |
| **Febin Nobel** | [@febin-04](https://github.com/febin-04) | Testing & Documentation |

---

## 📜 License

This project is licensed under the **MIT License**.  
You may use, modify, and distribute this software with proper attribution.

---

### 💡 Made with ❤️ by **Team Book My Vandi**
