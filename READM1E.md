🚗 Car Booking System (Java Swing + MySQL)

A fully functional Car Booking System built with Java Swing, following the MVC architecture.
This desktop app enables Admins, Sellers, and Users to manage, view, and book cars in real-time, with a connected MySQL database.

📁 CarBookingSystem (Root Folder)
│
├── 📁 src
│   ├── 📁 model
│   │   ├── Bookings.java          # Booking entity (stores user bookings)
│   │   ├── Car.java               # Car entity (stores car details)
│   │   ├── User.java              # User entity (stores admin/seller/user data)
│   │
│   ├── 📁 controller
│   │   ├── BookingController.java # Handles booking logic and validation
│   │   ├── CarController.java     # Handles car CRUD operations
│   │   ├── UserController.java    # Handles user login & signup
│   │
│   ├── 📁 view
│   │   ├── MainFrame.java         # Main GUI window
│   │   ├── MainFrame$BookingDialog.class   # Booking dialog
│   │   ├── MainFrame$AdminPanel$1.class    # Admin inner class
│   │   ├── ... (Other view-related GUI components)
│   │
│   ├── 📁 dao
│   │   └── (Future DAO classes for DB queries)
│   │
│   ├── 📁 database
│   │   └── DBConnection.java      # MySQL connection handler
│   │
│   ├── 📁 utils
│   │   └── UIUtils.java           # Utility methods for GUI styling and alerts
│   │
│   └── MainFrame.class            # Compiled main class
│
├── 📁 resources
│   ├── 📁 images                  # Car and UI images
│   │   ├── 3c785b81-d412-44da-8a9e-b071741517f8.png
│   │   ├── 72f8e19a-9120-406b-bff2-52d4cc1de827.png
│   │   └── ...
│   │
│   ├── 📁 screenshots             # App preview screenshots
│   │   ├── Admin Login.png
│   │   ├── Account Creation Confirmation.png
│   │   ├── Booking History.png
│   │   └── ...
│
└── 📁 bin                         # Compiled .class files




🧠 Features

✅ User Roles:

Admin — Manage cars, users, and view all bookings.

Seller — Add, update, and view their own cars.

User — Browse, rent, and view booking history.

✅ Functional Modules:

Login & Signup System

Car Browsing (with images & details)

Booking Dialog with Date Picker & Price Calculation

Admin Dashboard for management

Swing-based dynamic panels & dialogs

Database persistence using JDBC (MySQL)


| Component                | Technology                                        |
| ------------------------ | ------------------------------------------------- |
| **Programming Language** | Java (JDK 8+)                                     |
| **GUI Framework**        | Swing & AWT                                       |
| **Database**             | MySQL                                             |
| **Architecture**         | MVC (Model-View-Controller)                       |
| **Build/Run Tool**       | Java Compiler or IDE (IntelliJ, Eclipse, VS Code) |


🏃‍♂️ How to Run
▶️ Option 1: Using Command Line
cd CarBookingSystem/src
javac */*.java */*/*.java
java view.MainFrame

▶️ Option 2: Using an IDE

Open the CarBookingSystem project in your IDE.

Set the JDK version to Java 8 or higher.

Make sure the MySQL server is running.

Run the MainFrame.java file (inside view folder).

The GUI window should appear.

💾 JDBC Setup (Important)

Download and add MySQL Connector/J to your classpath:
👉 MySQL Connector/J Download Page

In your IDE:

Eclipse: Project → Properties → Java Build Path → Add External JARs → Select Connector/J

IntelliJ IDEA: File → Project Structure → Modules → Dependencies → Add JAR

🪟 Sample Screenshots
Login Page	Admin Panel	Booking Dialog

	
	
🧱 Future Enhancements

Add email notifications for bookings

Implement image upload feature for car listings

Password encryption for user security

Add search, sort, and filter for car browsing

Integrate with payment gateway (Stripe or PayPal)
