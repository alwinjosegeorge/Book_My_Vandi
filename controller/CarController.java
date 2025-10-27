package controller;

import database.DBConnection;
import model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CarController
 * CRUD methods for cars table.
 */
public class CarController {

    // Fetch all cars
    public static List<Car> fetchCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) cars.add(mapCar(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return cars;
    }

    // Find car by id
    public static Car findCarById(String id) {
        String sql = "SELECT * FROM cars WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapCar(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // Add a new car
    public static void addCar(Car c) {
        String sql = "INSERT INTO cars (id, name, model, price_per_day, category, status, image_path, owner_id, fuel_type, seats, transmission) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.id);
            ps.setString(2, c.name);
            ps.setString(3, c.model);
            ps.setDouble(4, c.pricePerDay);
            ps.setString(5, c.category);
            ps.setString(6, c.status);
            ps.setString(7, c.imagePath);
            ps.setString(8, c.ownerId);
            ps.setString(9, c.fuelType);
            ps.setInt(10, c.seats);
            ps.setString(11, c.transmission);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Update car
    public static void updateCar(Car c) {
        String sql = "UPDATE cars SET name=?, model=?, price_per_day=?, category=?, status=?, image_path=?, owner_id=?, fuel_type=?, seats=?, transmission=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.name);
            ps.setString(2, c.model);
            ps.setDouble(3, c.pricePerDay);
            ps.setString(4, c.category);
            ps.setString(5, c.status);
            ps.setString(6, c.imagePath);
            ps.setString(7, c.ownerId);
            ps.setString(8, c.fuelType);
            ps.setInt(9, c.seats);
            ps.setString(10, c.transmission);
            ps.setString(11, c.id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Delete car
    public static void deleteCar(String id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Helper: map ResultSet -> Car
    private static Car mapCar(ResultSet rs) throws SQLException {
        return new Car(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("model"),
                rs.getDouble("price_per_day"),
                rs.getString("category"),
                rs.getString("status"),
                rs.getString("image_path"),
                rs.getString("owner_id"),
                rs.getString("fuel_type"),
                rs.getInt("seats"),
                rs.getString("transmission")
        );
    }
}
