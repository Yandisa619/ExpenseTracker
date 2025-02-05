package gui;

import database.DatabaseHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExpenseForm extends JFrame {
    private JTextField categoryField, amountField, dateField, notesField;
    private JButton saveButton, toggleThemeButton;
    private JPanel formPanel, buttonPanel;
    private boolean isDarkMode = false;

    public ExpenseForm() {
        setTitle("Add Expense");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        categoryField = createTextField("Enter category...");
        amountField = createTextField("Enter amount...");
        dateField = createTextField("YYYY-MM-DD");
        notesField = createTextField("Enter notes (optional)");

        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        formPanel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        formPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 1;
        formPanel.add(notesField, gbc);


        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        saveButton = createButton("💾 Save", Color.BLUE);
        toggleThemeButton = createButton("🌙 Toggle Dark Mode", Color.BLACK);

        saveButton.addActionListener(e -> saveExpense());
        toggleThemeButton.addActionListener(e -> toggleTheme());

        buttonPanel.add(saveButton);
        buttonPanel.add(toggleThemeButton);


        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        applyLightMode();
    }


    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(15);
        textField.setText(placeholder);
        textField.setForeground(Color.BLACK);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(isDarkMode ? Color.WHITE : Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.BLACK);
                }
            }
        });

        return textField;
    }


    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            applyDarkMode();
        } else {
            applyLightMode();
        }
    }


    private void applyDarkMode() {
        getContentPane().setBackground(Color.BLACK);
        formPanel.setBackground(Color.BLACK);
        buttonPanel.setBackground(Color.BLACK);

        for (Component component : formPanel.getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(Color.WHITE);
            } else if (component instanceof JTextField) {
                component.setForeground(Color.WHITE);
                component.setBackground(Color.BLACK);
            }
        }
    }


    private void applyLightMode() {
        getContentPane().setBackground(Color.WHITE);
        formPanel.setBackground(Color.WHITE);
        buttonPanel.setBackground(Color.WHITE);

        for (Component component : formPanel.getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(Color.BLACK);
            } else if (component instanceof JTextField) {
                component.setForeground(Color.BLACK);
                component.setBackground(Color.WHITE);
            }
        }
    }


    private void saveExpense() {
        String category = categoryField.getText().trim();
        String amountText = amountField.getText().trim();
        String date = dateField.getText().trim();
        String notes = notesField.getText().trim();


        if (category.isEmpty() || amountText.isEmpty() || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields except 'Notes' are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // SQL Query to insert expense
        String sql = "INSERT INTO expenses (category, amount, date, notes) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, date);
            pstmt.setString(4, notes.isEmpty() ? null : notes);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Expense added successfully!");
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving expense. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseForm().setVisible(true));
    }
}
