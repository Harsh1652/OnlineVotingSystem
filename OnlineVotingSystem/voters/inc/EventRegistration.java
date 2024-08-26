import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class EventRegistration extends JFrame {
    private JLabel nameLabel, emailLabel, phoneLabel;
    private JTextField nameField, emailField, phoneField;
    private JButton registerButton;

    public EventRegistration() {
        setTitle("Event Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        nameLabel = new JLabel("Name:");
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Phone:");

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());

        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(phoneLabel);
        add(phoneField);
        add(new JLabel());
        add(registerButton);
    }

    private class RegisterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            try {
                // Connect to the database
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventdb", "root", "password");

                // Create a prepared statement
                String query = "INSERT INTO registrations (name, email, phone) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);

                // Execute the query
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Registration successful!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed!");
                }

                // Close the database connection
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void clearFields() {
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
        }
    }

    public static void main(String[] args) {
        EventRegistration frame = new EventRegistration();
        frame.setVisible(true);
    }
}