# CarRentalMVC (Swing + SQLite)
This is a student-friendly MVC-structured Java Swing project for a Car Rental system.

## What is included
- `database/DBConnection.java` — SQLite singleton helper.
- `model/` — `Car`, `User`, `Booking` POJOs.
- `controller/` — `UserController`, `CarController`, `BookingController`.
- `view/MainFrame.java` — main Swing UI (contains the main panels).
- `images/` — folder should contain car images and a `placeholder.png` (not included).
- No `.db` file is included (use your existing `carrental.db`).

## How to run (Windows)
1. Install JDK (17+).
2. Put your `carrental.db` in the project root (same folder as this README).
3. Download SQLite JDBC jar and place it in project root as `sqlite-jdbc.jar`.
   - https://github.com/xerial/sqlite-jdbc/releases
4. Compile:
   ```
   javac -cp ".;sqlite-jdbc.jar" database/*.java model/*.java controller/*.java view/*.java
   ```
5. Run:
   ```
   java -cp ".;sqlite-jdbc.jar" view.MainFrame
   ```

For Mac/Linux replace `;` with `:` in classpath.

## Notes
- Passwords are plain-text in DB (for demo only) — do not use in production.
- If you run into driver errors, ensure `sqlite-jdbc.jar` is present and classpath is correct.
