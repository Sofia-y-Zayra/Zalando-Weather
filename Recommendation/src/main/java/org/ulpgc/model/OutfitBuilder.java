package org.ulpgc.model;

import org.ulpgc.model.utils.ColorMatcher;
import org.ulpgc.dacd.model.Product;

import java.util.List;

public class OutfitBuilder {

    public String build(String city, double temp, String desc, String gender, List<Product> products) {

        List<Product> filtered = products.stream()
                .filter(p -> p.getCategory().toUpperCase().contains(gender.toUpperCase()))
                .toList();

        // =====================
        // 1. PANTALÓN
        // =====================

        List<Product> pantsList = filtered.stream()
                .filter(p -> p.getCategory().contains("PANTALON"))
                .toList();

        Product pants = pantsList.stream().findFirst().orElse(null);

        // =====================
        // 2. CAMISETA (según ColorMatcher)
        // =====================

        Product top = null;

        if (pants != null) {

            List<String> validColors = ColorMatcher.matchTops(pants.getColor());
            top = filtered.stream()
                    .filter(p -> p.getCategory().contains("CAMISETA"))
                    .filter(p -> p.getColor() != null &&
                            validColors.stream().anyMatch(c ->
                                    p.getColor().toLowerCase().contains(c)))
                    .findFirst()
                    .orElse(null);
        }

        // =====================
        // 3. CHAQUETA
        // =====================

        Product jacket = null;

        if (temp < 20) {
            jacket = filtered.stream()
                    .filter(p -> p.getCategory().contains("CHAQUETA"))
                    .findFirst()
                    .orElse(null);
        }

        // =====================
        // OUTPUT
        // =====================

        double total = 0;
        StringBuilder sb = new StringBuilder();

        sb.append("Clima en ").append(city).append("\n");
        sb.append("Temperatura: ").append(temp).append(" grados\n");
        sb.append("Descripcion: ").append(desc).append("\n\n");

        sb.append("Outfit recomendado (").append(gender).append(")\n\n");

        if (top != null) {
            sb.append("Camiseta: ").append(top.getName()).append("\n");
            sb.append("Precio: ").append(top.getPrice()).append("\n\n");
            sb.append("<img src='data:image/jpeg;base64,")
                    .append(top.getImageUrl())
                    .append("' width='200'/><br><br>");
            total += top.getPrice();
        }

        if (pants != null) {
            sb.append("Pantalón: ").append(pants.getName()).append("\n");
            sb.append("Precio: ").append(pants.getPrice()).append("\n\n");
            sb.append("<img src='data:image/jpeg;base64,")
                    .append(pants.getImageUrl())
                    .append("' width='200'/><br><br>");
            total += pants.getPrice();
        }

        if (jacket != null) {
            sb.append("Chaqueta: ").append(jacket.getName()).append("\n");
            sb.append("Precio: ").append(jacket.getPrice()).append("\n\n");
            sb.append("<img src='data:image/jpeg;base64,")
                    .append(jacket.getImageUrl())
                    .append("' width='200'/><br><br>");
            total += jacket.getPrice();
        }

        sb.append("Total outfit: ").append(String.format("%.2f", total)).append("euros");

        return sb.toString();
    }
}