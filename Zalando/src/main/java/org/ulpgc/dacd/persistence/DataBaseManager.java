package org.ulpgc.dacd.persistence;

import org.ulpgc.dacd.model.Product;

import java.sql.*;

public class DataBaseManager {

    private static final String URL = "jdbc:sqlite:products.db";

    public static void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "price REAL," +
                "category TEXT," +
                "brand TEXT," +
                "color TEXT," +
                "imageUrl TEXT," +
                "date TEXT" +
                ")";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertProduct(Product p) {

        String sql = "INSERT INTO products(name, price, category, brand, color, imageUrl, date) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, p.getName());
            statement.setDouble(2, p.getPrice());
            statement.setString(3, p.getCategory());
            statement.setString(4, p.getBrand());
            statement.setString(5, p.getColor());
            statement.setString(6, p.getImageUrl());
            statement.setString(7, p.getDate());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
