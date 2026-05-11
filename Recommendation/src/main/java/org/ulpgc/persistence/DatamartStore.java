package org.ulpgc.persistence;

import java.sql.*;

public class DatamartStore {

    private final String dbPath = "jdbc:sqlite:datamart_final.db";

    public DatamartStore() {
        initDatabase();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbPath);
    }

    private void initDatabase() {

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS weather_status (
                    city TEXT PRIMARY KEY,
                    temperature REAL,
                    description TEXT,
                    timestamp TEXT
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS product_catalog (
                    id TEXT PRIMARY KEY,
                    name TEXT,
                    price REAL,
                    category TEXT,
                    brand TEXT,
                    color TEXT,
                    imageUrl TEXT,
                    timestamp TEXT
                )
            """);

            System.out.println("Datamart inicializado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}