package com.mycompany.mikimupp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL
            = "jdbc:mysql://localhost:3306/mikimupp";
    private static final String USER = "root";
    private static final String PASSWORD = "Daffa123";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    URL, USER, PASSWORD
            );

            System.out.println("Koneksi database berhasil!");
            return connection;

        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}
