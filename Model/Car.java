package model;

/**
 * Car model
 * Simple POJO to represent a car record from the database.
 *
 * Fields mirror the 'cars' table in your DB:
 * id, name, model, price_per_day, category, status, image_path, owner_id, fuel_type, seats, transmission.
 */
public class Car {
    public String id;
    public String name;
    public String model;
    public double pricePerDay;
    public String category;
    public String status;
    public String imagePath;
    public String ownerId;
    public String fuelType;
    public int seats;
    public String transmission;

    public Car() {}

    public Car(String id, String name, String model, double pricePerDay, String category, String status,
               String imagePath, String ownerId, String fuelType, int seats, String transmission) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.category = category;
        this.status = status;
        this.imagePath = imagePath;
        this.ownerId = ownerId;
        this.fuelType = fuelType;
        this.seats = seats;
        this.transmission = transmission;
    }

    @Override
    public String toString() {
        return name + " (" + model + ")";
    }
}
