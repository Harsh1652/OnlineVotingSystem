import java.sql.*;
import java.util.Scanner;

public class CRMS {
    public class CarRentalManagementSystem {

        // Database connection details
        static final String DB_URL = "jdbc:mysql://localhost:3306/car_rental_db";
        static final String USER = "root";
        static final String PASS = "your_password";  // Replace with your MySQL password

        public static void main(String[] args) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Scanner scanner = new Scanner(System.in)) {

                // Create database schema if not exists
                createSchema(conn);

                System.out.println("Car Rental Management System");

                while (true) {
                    System.out.println("\nMenu:");
                    System.out.println("1. Add a Car");
                    System.out.println("2. Rent a Car");
                    System.out.println("3. Return a Car");
                    System.out.println("4. Search Available Cars");
                    System.out.println("5. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            addCar(conn, scanner);
                            break;
                        case 2:
                            rentCar(conn, scanner);
                            break;
                        case 3:
                            returnCar(conn, scanner);
                            break;
                        case 4:
                            searchAvailableCars(conn);
                            break;
                        case 5:
                            System.out.println("Exiting the system...");
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Function to create the necessary schema
        private static void createSchema(Connection conn) {
            try (Statement stmt = conn.createStatement()) {

                String createCarsTable = "CREATE TABLE IF NOT EXISTS cars (" +
                        "car_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "brand VARCHAR(255) NOT NULL, " +
                        "model VARCHAR(255) NOT NULL, " +
                        "base_price_per_day DOUBLE NOT NULL, " +
                        "is_available BOOLEAN DEFAULT TRUE" +
                        ")";

                String createRentalsTable = "CREATE TABLE IF NOT EXISTS rentals (" +
                        "rental_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "car_id INT NOT NULL, " +
                        "customer_name VARCHAR(255) NOT NULL, " +
                        "days INT NOT NULL, " +
                        "FOREIGN KEY (car_id) REFERENCES cars(car_id)" +
                        ")";

                stmt.executeUpdate(createCarsTable);
                stmt.executeUpdate(createRentalsTable);

                System.out.println("Database schema created successfully.");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Function to add a car to the database
        private static void addCar(Connection conn, Scanner scanner) {
            try {
                System.out.print("Enter brand: ");
                String brand = scanner.next();
                System.out.print("Enter model: ");
                String model = scanner.next();
                System.out.print("Enter price per day: ");
                double price = scanner.nextDouble();

                String sql = "INSERT INTO cars (brand, model, base_price_per_day, is_available) VALUES (?, ?, ?, 1)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, brand);
                    pstmt.setString(2, model);
                    pstmt.setDouble(3, price);
                    pstmt.executeUpdate();
                    System.out.println("Car added successfully!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Function to rent a car
        private static void rentCar(Connection conn, Scanner scanner) {
            try {
                System.out.print("Enter car ID: ");
                int carId = scanner.nextInt();
                System.out.print("Enter customer name: ");
                String customerName = scanner.next();
                System.out.print("Enter number of days: ");
                int days = scanner.nextInt();

                String checkAvailability = "SELECT is_available FROM cars WHERE car_id=?";
                try (PreparedStatement pstmt = conn.prepareStatement(checkAvailability)) {
                    pstmt.setInt(1, carId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next() && rs.getBoolean("is_available")) {
                            String rentCar = "INSERT INTO rentals (car_id, customer_name, days) VALUES (?, ?, ?)";
                            String updateAvailability = "UPDATE cars SET is_available=0 WHERE car_id=?";

                            try (PreparedStatement rentStmt = conn.prepareStatement(rentCar);
                                 PreparedStatement updateStmt = conn.prepareStatement(updateAvailability)) {

                                rentStmt.setInt(1, carId);
                                rentStmt.setString(2, customerName);
                                rentStmt.setInt(3, days);
                                rentStmt.executeUpdate();

                                updateStmt.setInt(1, carId);
                                updateStmt.executeUpdate();

                                System.out.println("Car rented successfully!");
                            }
                        } else {
                            System.out.println("Car is not available or does not exist.");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Function to return a car
        private static void returnCar(Connection conn, Scanner scanner) {
            try {
                System.out.print("Enter car ID: ");
                int carId = scanner.nextInt();

                String checkRental = "SELECT * FROM rentals WHERE car_id=?";
                try (PreparedStatement pstmt = conn.prepareStatement(checkRental)) {
                    pstmt.setInt(1, carId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            String deleteRental = "DELETE FROM rentals WHERE car_id=?";
                            String updateAvailability = "UPDATE cars SET is_available=1 WHERE car_id=?";

                            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteRental);
                                 PreparedStatement updateStmt = conn.prepareStatement(updateAvailability)) {

                                deleteStmt.setInt(1, carId);
                                deleteStmt.executeUpdate();

                                updateStmt.setInt(1, carId);
                                updateStmt.executeUpdate();

                                System.out.println("Car returned successfully!");
                            }
                        } else {
                            System.out.println("Car is not currently rented.");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Function to search for available cars
        private static void searchAvailableCars(Connection conn) {
            try {
                String sql = "SELECT * FROM cars WHERE is_available=1";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {

                    System.out.println("Available Cars:");
                    System.out.println("Car ID | Brand | Model | Price Per Day");
                    while (rs.next()) {
                        System.out.println(rs.getInt("car_id") + " | " +
                                rs.getString("brand") + " | " +
                                rs.getString("model") + " | " +
                                rs.getDouble("base_price_per_day"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

