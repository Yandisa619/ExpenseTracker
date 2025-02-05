package gui;

import database.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signupButton, toggleThemeButton;
    private JPanel panel;
    private boolean isDarkMode = false;

    public SignupForm() {
        setTitle("Expense Tracker - Sign Up");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        signupButton = new JButton("Sign Up");
        signupButton.setPreferredSize(new Dimension(120, 30));
        signupButton.addActionListener(e -> registerUser());
        panel.add(signupButton, gbc);

        gbc.gridy = 3;
        toggleThemeButton = new JButton("🌙 Dark Mode");
        toggleThemeButton.setPreferredSize(new Dimension(120, 30));
        toggleThemeButton.addActionListener(e -> toggleDarkMode());
        panel.add(toggleThemeButton, gbc);

        add(panel);

        applyLightMode();

    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            applyDarkMode();
        } else {
            applyLightMode();
        }
    }



private void applyDarkMode() {
    panel.setBackground(Color.BLACK);


    usernameField.setBackground(Color.BLACK);
    usernameField.setForeground(Color.WHITE);
    passwordField.setBackground(Color.BLACK);
    passwordField.setForeground(Color.WHITE);


    signupButton.setBackground(Color.BLACK);
    signupButton.setForeground(Color.WHITE);
    toggleThemeButton.setBackground(Color.BLACK);
    toggleThemeButton.setForeground(Color.WHITE);
    toggleThemeButton.setText("☀ Light Mode");
}

    private void applyLightMode() {
        panel.setBackground(Color.WHITE);


        usernameField.setBackground(Color.WHITE);
        usernameField.setForeground(Color.BLACK);
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.BLACK);


        signupButton.setBackground(Color.LIGHT_GRAY);
        signupButton.setForeground(Color.BLACK);
        toggleThemeButton.setBackground(Color.LIGHT_GRAY);
        toggleThemeButton.setForeground(Color.BLACK);
        toggleThemeButton.setText("🌙 Dark Mode");

    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Account created successfully!");
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating account.");
        }
    }
}