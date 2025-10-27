package controller;

import database.DBConnection;
import model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingController
 * Create and manage bookings, including fetching by user or all bookings and updating status.
 */
public class BookingController {

    // Add a new booking (status will be inserted from Booking.status)
    public static void addBooking(Booking b) {
        String sql = "INSERT INTO bookings (id, car_id, user_id, pickup_place, pickup_date, pickup_time, days, total_price, status) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.id);
            ps.setString(2, b.carId);
            ps.setString(3, b.userId);
            ps.setString(4, b.pickupPlace);
            ps.setString(5, b.pickupDate);
            ps.setString(6, b.pickupTime);
            ps.setInt(7, b.days);
            ps.setDouble(8, b.totalPrice);
            ps.setString(9, b.status);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Fetch all bookings (admin view)
    public static List<Booking> fetchBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapBooking(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // Fetch bookings by user
    public static List<Booking> fetchBookingsByUserId(String userId) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapBooking(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // Update booking status
    public static void updateBookingStatus(String bookingId, String newStatus) {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setString(2, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Helper: map resultset -> Booking
    private static Booking mapBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getString("id"),
                rs.getString("car_id"),
                rs.getString("user_id"),
                rs.getString("pickup_place"),
                rs.getString("pickup_date"),
                rs.getString("pickup_time"),
                rs.getInt("days"),
                rs.getDouble("total_price"),
                rs.getString("status")
        );
    }
}
