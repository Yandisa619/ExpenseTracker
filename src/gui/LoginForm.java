package gui;
import database.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton, toggleThemeButton;
    private JPanel panel;
    private boolean isDarkMode = false;

    public LoginForm() {
        setTitle("Expense Tracker - Login");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing


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
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 30));
        loginButton.addActionListener(e -> login());
        panel.add(loginButton, gbc);

        gbc.gridy = 3;
        signupButton = new JButton("Sign Up");
        signupButton.setPreferredSize(new Dimension(120, 30));
        signupButton.addActionListener(e -> openSignupForm());
        panel.add(signupButton, gbc);


        gbc.gridy = 4;
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
        passwordField.setBackground(Color.BLACK);
        usernameField.setForeground(Color.WHITE);
        passwordField.setForeground(Color.WHITE);
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        signupButton.setBackground(Color.BLACK);
        signupButton.setForeground(Color.WHITE);
        toggleThemeButton.setBackground(Color.BLACK);
        toggleThemeButton.setForeground(Color.WHITE);
        toggleThemeButton.setText("☀ Light Mode");
    }

    private void applyLightMode() {
        panel.setBackground(Color.WHITE);
        usernameField.setBackground(Color.WHITE);
        passwordField.setBackground(Color.WHITE);
        loginButton.setBackground(Color.LIGHT_GRAY);
        loginButton.setForeground(Color.BLACK);
        signupButton.setBackground(Color.LIGHT_GRAY);
        signupButton.setForeground(Color.BLACK);
        toggleThemeButton.setBackground(Color.LIGHT_GRAY);
        toggleThemeButton.setForeground(Color.BLACK);
        toggleThemeButton.setText("🌙 Dark Mode");
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new Dashboard().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error logging in.");
        }
    }

    private void openSignupForm() {
        new SignupForm().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}


