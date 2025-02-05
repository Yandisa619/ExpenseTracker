package gui;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private boolean isDarkMode = false;

    public Dashboard() {
        setTitle("Expense Tracker - Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JLabel welcomeLabel = new JLabel("WELCOME TO THE EXPENSE TRACKER", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addExpenseButton = new JButton("➕ Add Expense");
        JButton viewExpensesButton = new JButton("📊 View Expenses");
        JButton toggleThemeButton = new JButton("🌙 Toggle Dark Mode");

        addExpenseButton.addActionListener(e -> new ExpenseForm().setVisible(true));
        toggleThemeButton.addActionListener(e -> toggleTheme());

        buttonPanel.add(addExpenseButton);
        buttonPanel.add(viewExpensesButton);
        buttonPanel.add(toggleThemeButton);

        add(welcomeLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        applyLightMode();
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
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                component.setBackground(Color.BLACK);
            } else if (component instanceof JLabel) {
                component.setForeground(Color.WHITE);
            } else if (component instanceof JButton) {
                component.setForeground(Color.WHITE);
                component.setBackground(Color.BLACK);
            }
        }
    }

    private void applyLightMode() {
        getContentPane().setBackground(Color.WHITE);
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                component.setBackground(Color.WHITE);
            } else if (component instanceof JLabel) {
                component.setForeground(Color.BLACK);
            } else if (component instanceof JButton) {
                component.setForeground(Color.BLACK);
                component.setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}


