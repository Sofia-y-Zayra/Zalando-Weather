package org.ulpgc.persistence;

import java.sql.*;

public class DatamartStore {
    private final String dbPath = "jdbc:sqlite:datamart.db";

    public DatamartStore() {
        initDatabase();
    }

    private void initDatabase() {
        try (Connection conn = DriverManager.getConnection(dbPath);
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS weather_status (
                    city TEXT PRIMARY KEY,
                    temp REAL,
                    condition TEXT,
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

    public void updateWeather(String city, double temp, String condition, String ts) {
        String sql = "INSERT OR REPLACE INTO weather_status(city, temp, condition, timestamp) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, city);
            pstmt.setDouble(2, temp);
            pstmt.setString(3, condition);
            pstmt.setString(4, ts);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
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
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // NUEVO: Método para obtener el clima actual de una ciudad
    public String getWeatherFor(String city) {
        String sql = "SELECT * FROM weather_status WHERE city = ?";
        try (Connection conn = DriverManager.getConnection(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return String.format("Ciudad: %s | Temp: %.1f°C | Condición: %s",
                        rs.getString("city"), rs.getDouble("temp"), rs.getString("condition"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "No hay datos recientes para " + city;
    }

    // NUEVO: Lógica de Recomendación (Valor añadido del Sprint 3)
    public String getSmartRecommendation(String city) {
        String sql = "SELECT temp, condition FROM weather_status WHERE city = ?";
        try (Connection conn = DriverManager.getConnection(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double temp = rs.getDouble("temp");
                String cond = rs.getString("condition").toLowerCase();
                if (cond.contains("rain")) return "Está lloviendo en " + city + ". Te recomendamos un Chubasquero de Zalando.";
                if (temp < 15) return "Hace frío en " + city + " (" + temp + "°C). Sugerimos ver Abrigos en Zalando.";
                if (temp > 25) return "Hace calor en " + city + " (" + temp + "°C). ¡Ponte una Camiseta ligera!";
                return "Clima templado en " + city + ". Una Sudadera es buena opción.";
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "Sin datos climáticos para recomendar ropa en " + city;
    }
}