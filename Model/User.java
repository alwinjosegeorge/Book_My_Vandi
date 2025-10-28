package model;

/**
 * User model
 * Mirrors the 'users' table:
 * id, username, password, role, contact
 *
 * Note: passwords stored as plain text for the demo (not recommended for production). 
 */
public class User {
    public String id;
    public String username;
    public String password;
    public String role;
    public String contact;

    public User() {}

    public User(String id, String username, String password, String role, String contact) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.contact = contact;
    }

    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}
