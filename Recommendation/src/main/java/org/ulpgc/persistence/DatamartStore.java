package org.ulpgc.persistence;

import java.sql.*;

public class DatamartStore {
    private final String dbPath = "jdbc:sqlite:datamart_final.db";

    public DatamartStore() {
        initDatabase();
    }

    private void initDatabase() {
        try (Connection conn = DriverManager.getConnection(dbPath);
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
                    timestamp TEXT
                )
            """);

            System.out.println("Datamart inicializado y listo.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateWeather(String city, double temperature, String description, String ts) {
        String sql = "INSERT OR REPLACE INTO weather_status(city, temperature, description, timestamp) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, city);
            pstmt.setDouble(2, temperature);
            pstmt.setString(3, description);
            pstmt.setString(4, ts);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upsertProduct(String id, String name, double price, String category, String ts) {
        String sql = "INSERT OR REPLACE INTO product_catalog(id, name, price, category, timestamp) VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setDouble(3, price);
            pstmt.setString(4, category);
            pstmt.setString(5, ts);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getWeatherFor(String city) {
        String sql = "SELECT * FROM weather_status WHERE city = ?";
        try (Connection conn = DriverManager.getConnection(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return String.format("Ciudad: %s | Temp: %.1f°C | Estado: %s",
                        rs.getString("city"), rs.getDouble("temperature"), rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No hay datos para " + city;
    }


    public String getSmartRecommendation(String city) {
        String sql = "SELECT temperature, description FROM weather_status WHERE city = ?";
        try (Connection conn = DriverManager.getConnection(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double temp = rs.getDouble("temperature");
                String desc = rs.getString("description").toLowerCase();

                if (desc.contains("lluvia")) return "Está lloviendo en " + city + ". Te recomendamos un Chubasquero de Zalando.";
                if (temp < 15) return "Hace frío en " + city + " (" + temp + "°C). Busca un Abrigo en nuestro catálogo.";
                if (temp >= 15 && temp < 25) return "Clima agradable en " + city + ". Una Sudadera o Chaqueta ligera va bien.";
                return "Hace calor en " + city + " (" + temp + "°C). ¡Ponte una Camiseta de manga corta!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Sin datos climáticos suficientes para recomendar en " + city;
    }
}