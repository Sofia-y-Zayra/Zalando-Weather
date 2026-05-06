package org.ulpgc.persistence;

import org.ulpgc.dacd.model.Product;

import java.sql.*;
import java.util.*;

public class ProductRepository {

    private final DatamartStore db;

    public ProductRepository(DatamartStore db) {
        this.db = db;
    }

    public void save(Product p, String ts) {

        String sql = """
            INSERT OR REPLACE INTO product_catalog
            VALUES(?,?,?,?,?,?,?,?)
        """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setString(1, UUID.randomUUID().toString());

            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getCategory());
            ps.setString(5, p.getBrand());
            ps.setString(6, p.getColor());
            ps.setString(7, p.getImageUrl());
            ps.setString(8, ts);

            ps.executeUpdate();

            System.out.println("Guardado producto: " + p.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Product> getAll() {

        List<Product> list = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement("SELECT * FROM product_catalog")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product p = new Product();

                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setCategory(rs.getString("category"));
                p.setBrand(rs.getString("brand"));
                p.setColor(rs.getString("color"));
                p.setImageUrl(rs.getString("imageUrl"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}