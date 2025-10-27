 🚗 CarRentalMultiRole — Java Swing App

**CarRentalMultiRole** is a simple **multi-role car rental and dealership demo application** built with **Java Swing (Java 17+)**.  
It allows users to log in as **Admin**, **Seller**, or **User**, with different permissions for each role.  
This project is *in-memory only* — no database is required — and it demonstrates clean UI design, file handling, and basic CRUD logic.

---

## 📋 Features

### 👥 Multi-role Login
- **Admin** → Can view all users and manage all cars.  
- **Seller** → Can add, update, or delete their cars and upload images.  
- **User** → Can browse cars and view car details and images.  

### 🚘 Car Management
- Add new cars with price, status, and image.  
- Update or delete existing cars.  
- Upload and store images in the `./images/` folder.  
- Automatically generates new car IDs (C001, C002, ...).

### 🖼️ Image Handling
- Uploaded images are copied to a local folder `images/`.  
- Scaled image thumbnails are displayed in tables and previews.

### 🧠 In-Memory Data
- No database required — all data is stored in lists during runtime.  
- Demo users and sample cars are preloaded automatically.

---

## 🧑‍💻 Demo Accounts

| Role   | Username       | Password   |
|--------|----------------|-------------|
| Admin  | `admin@demo`   | `admin123`  |
| Seller | `seller@demo`  | `seller123` |
| User   | `user@demo`    | `user123`   |

---

## 🗂️ Project Structure

```
CarRentalMultiRole.java    # Main single-file Swing application
images/                    # Folder created automatically for uploaded images
README.md                  # This file
```

---

## 🏃 How to Run

### Requirements
- **Java 17 or later**  
- A terminal or IDE (VS Code / IntelliJ / Eclipse)

### Steps
1. Download or copy `CarRentalMultiRole.java`.
2. Open a terminal in the same folder.
3. Compile:
   ```bash
   javac CarRentalMultiRole.java
   ```
4. Run:
   ```bash
   java CarRentalMultiRole
   ```
5. Log in using one of the demo accounts.

---

## 🧩 Role Overview

### 👨‍💼 Admin Dashboard
- View **all cars** (manage/edit/delete).  
- View list of **all users** (read-only).

### 💼 Seller Dashboard
- Add new cars with details and images.  
- Update or delete existing listings.  
- View uploaded image previews instantly.

### 🚙 User Dashboard
- Browse available cars.  
- View car details and full-size image previews.

---

## 🛠️ Key Concepts Used

- **Java Swing & AWT** (UI components, event handling)
- **CardLayout** for switching panels
- **JTabbedPane** for multi-role dashboards
- **FileChooser & ImageIO** for uploading images
- **Table Models & Image rendering in JTable**
- **In-memory lists as data store**
- **MVC-inspired structure within a single file**

---

## ⚠️ Notes

- All data resets when you close the app (no persistence).
- Make sure you have permission to create folders/files in the directory (for image uploads).
- This project is meant for learning/demo purposes — not for production.

---

## 🧑‍🏫 Author

**Barry (Student Project)**  
🎓 B.Tech — Computer Science  
💡 Exploring Java, Swing, and UI design  
