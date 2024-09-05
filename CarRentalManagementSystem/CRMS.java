import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/CRMS")
public class CRMS extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CRMS.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        String dbUrl = "jdbc:mysql://localhost:8080/CarRental";
        String dbUser = "root";
        String dbPassword = "harsh16";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {

            if ("addCar".equals(action)) {
                String brand = request.getParameter("brand");
                String model = request.getParameter("model");
                double price = Double.parseDouble(request.getParameter("price"));

                String sql = "INSERT INTO cars (brand, model, base_price_per_day, is_available) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, brand);
                    stmt.setString(2, model);
                    stmt.setDouble(3, price);
                    stmt.setBoolean(4, true);

                    int result = stmt.executeUpdate();
                    out.println(result > 0 ? "Car added successfully!" : "Failed to add car.");
                }

            } else if ("rentCar".equals(action)) {
                int carId = Integer.parseInt(request.getParameter("carId"));
                String customerName = request.getParameter("customerName");
                int days = Integer.parseInt(request.getParameter("days"));

                String selectCarQuery = "SELECT is_available FROM cars WHERE car_id = ?";
                try (PreparedStatement selectStmt = conn.prepareStatement(selectCarQuery)) {
                    selectStmt.setInt(1, carId);
                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next() && rs.getBoolean("is_available")) {
                            String rentCarQuery = "INSERT INTO rentals (car_id, customer_name, days) VALUES (?, ?, ?)";
                            try (PreparedStatement rentStmt = conn.prepareStatement(rentCarQuery)) {
                                rentStmt.setInt(1, carId);
                                rentStmt.setString(2, customerName);
                                rentStmt.setInt(3, days);
                                int rentResult = rentStmt.executeUpdate();

                                String updateCarQuery = "UPDATE cars SET is_available = false WHERE car_id = ?";
                                try (PreparedStatement updateStmt = conn.prepareStatement(updateCarQuery)) {
                                    updateStmt.setInt(1, carId);
                                    updateStmt.executeUpdate();
                                }

                                System.out.println(rentResult > 0 ? "Car rented successfully!" : "Failed to rent car.");
                            }
                        } else {
                            System.out.println("Car is not available or not found.");
                        }
                    }
                }

            } else if ("returnCar".equals(action)) {
                int carId = Integer.parseInt(request.getParameter("carId"));

                String selectRentalQuery = "SELECT * FROM rentals WHERE car_id = ?";
                try (PreparedStatement selectStmt = conn.prepareStatement(selectRentalQuery)) {
                    selectStmt.setInt(1, carId);
                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            String deleteRentalQuery = "DELETE FROM rentals WHERE car_id = ?";
                            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteRentalQuery)) {
                                deleteStmt.setInt(1, carId);
                                deleteStmt.executeUpdate();
                            }

                            String updateCarQuery = "UPDATE cars SET is_available = true WHERE car_id = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateCarQuery)) {
                                updateStmt.setInt(1, carId);
                                updateStmt.executeUpdate();
                            }

                            out.println("Car returned successfully!");
                        } else {
                            out.println("Car was not rented.");
                        }
                    }
                }

            } else if ("searchCar".equals(action)) {
                String selectAvailableCarsQuery = "SELECT * FROM cars WHERE is_available = true";
                try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectAvailableCarsQuery)) {
                    out.println("<table>");
                    out.println("<tr><th>Car ID</th><th>Brand</th><th>Model</th><th>Price Per Day</th></tr>");
                    while (rs.next()) {
                        out.println("<tr><td>" + rs.getInt("car_id") + "</td><td>" + rs.getString("brand") + "</td><td>" + rs.getString("model") + "</td><td>" + rs.getDouble("base_price_per_day") + "</td></tr>");
                    }
                    out.println("</table>");
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            out.println("Error: " + e.getMessage());
        }
    }
}
