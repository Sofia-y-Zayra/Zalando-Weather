package org.ulpgc.model;

import org.ulpgc.model.utils.ColorMatcher;
import org.ulpgc.dacd.model.Product;
import java.util.List;

public class OutfitBuilder {

    private Product randomPick(List<Product> list) {
        if (list.isEmpty()) return null;
        return list.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(list.size()));
    }

    public String build(String city, double temp, String desc, String gender, List<Product> products) {
        List<Product> filtered = products.stream()
                .filter(p -> p.getCategory().toUpperCase().contains(gender.toUpperCase()))
                .toList();


        List<Product> pantsList = filtered.stream()
                .filter(p -> p.getCategory().toUpperCase().contains("PANTALON"))
                .toList();
        Product pants = randomPick(pantsList);

        Product top = null;
        if (pants != null) {
            List<String> validColors = ColorMatcher.matchTops(pants.getColor());
            List<Product> tops = filtered.stream()
                    .filter(p -> p.getCategory().toUpperCase().contains("CAMISETA"))
                    .filter(p -> p.getColor() != null && validColors.stream().anyMatch(c -> p.getColor().toLowerCase().contains(c.toLowerCase())))
                    .toList();
            top = randomPick(tops);
        }

        Product jacket = (temp < 20) ? randomPick(filtered.stream().filter(p -> p.getCategory().toUpperCase().contains("CHAQUETA")).toList()) : null;


        StringBuilder sb = new StringBuilder();
        double total = 0;

        if (top != null) { sb.append(createProductCard("Prenda Superior", top)); total += top.getPrice(); }
        if (pants != null) { sb.append(createProductCard("Pantalón", pants)); total += pants.getPrice(); }
        if (jacket != null) { sb.append(createProductCard("Abrigo", jacket)); total += jacket.getPrice(); }

        if (total == 0) return "<p style='grid-column: 1/-1; text-align:center;'>No hay productos suficientes en el Datamart para esta combinación.</p>";

        sb.append("<div style='grid-column: 1/-1; text-align: center; margin-top: 20px; font-size: 1.4em;'>");
        sb.append("Total Outfit: <b style='color:#e74c3c;'>").append(String.format("%.2f", total)).append(" €</b></div>");

        return sb.toString();
    }

    private String createProductCard(String label, Product p) {
            return String.format("""
        <div class='card'>
            <div style='color: #888; font-size: 0.75em; margin-bottom: 5px; text-transform: uppercase;'>%s</div>
            <!-- El formato correcto para imagen como texto -->
            <img src="data:image/jpeg;base64,%s" alt="prenda">
            <h3>%s</h3>
            <div class='price'>%.2f €</div>
        </div>
        """,
                    label,
                    p.getImageUrl(),
                    p.getName(),
                    p.getPrice());
        }
}