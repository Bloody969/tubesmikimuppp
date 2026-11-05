package com.mycompany.mikimupp.database;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();

        if (connection != null) {
            System.out.println("Database siap digunakan!");
        } else {
            System.out.println("Database belum terhubung.");
        }
    }
}
