package model;

/**
 * Booking model
 * Mirrors 'bookings' table:
 * id, car_id, user_id, pickup_place, pickup_date, pickup_time, days, total_price, status 
 */
public class Booking {
    public String id;
    public String carId;
    public String userId;
    public String pickupPlace;
    public String pickupDate;
    public String pickupTime;
    public int days;
    public double totalPrice;
    public String status; // "Pending", "Confirmed", "Rejected"

    public Booking() {}

    public Booking(String id, String carId, String userId, String pickupPlace, String pickupDate, String pickupTime,
                   int days, double totalPrice, String status) {
        this.id = id;
        this.carId = carId;
        this.userId = userId;
        this.pickupPlace = pickupPlace;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.days = days;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    @Override
   
    
    
    public String toString() {
        
        
        return id + " - " + carId + " for " + days + " day(s) [" + status + "]";
    }
}
