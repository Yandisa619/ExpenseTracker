package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:expenses.db";


    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found!");
            e.printStackTrace();
        }
    }


    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Connected to database successfully.");
            return conn;
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }


    public static void createTables() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            if (conn != null) {
                stmt.execute(createUsersTable());
                stmt.execute(createExpensesTable());
                System.out.println("Tables created successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating tables!");
            e.printStackTrace();
        }
    }


    private static String createUsersTable() {
        return "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL" +
                ");";
    }


    private static String createExpensesTable() {
        return "CREATE TABLE IF NOT EXISTS expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "category TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "date TEXT NOT NULL," +
                "notes TEXT" +
                ");";
    }


    public static boolean isDatabaseAccessible() {
        try (Connection conn = connect()) {
            return conn != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        createTables();
    }
}

