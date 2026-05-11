package org.ulpgc.persistence;

import java.sql.*;

public class WeatherRepository {

    private final DatamartStore db;

    public WeatherRepository(DatamartStore db) {
        this.db = db;
    }

    public void save(String city, double temp, String desc, String ts) {

        String sql = """
            INSERT OR REPLACE INTO weather_status
            VALUES(?,?,?,?)
        """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, city);
            ps.setDouble(2, temp);
            ps.setString(3, desc);
            ps.setString(4, ts);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getTemperature(String city) {

        try (Connection conn = db.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("SELECT temperature FROM weather_status WHERE city=?")) {

            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getDouble(1);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return 20;
    }

    public String getDescription(String city) {

        try (Connection conn = db.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("SELECT description FROM weather_status WHERE city=?")) {

            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString(1);

        } catch (Exception e) {}

        return "";
    }
}
