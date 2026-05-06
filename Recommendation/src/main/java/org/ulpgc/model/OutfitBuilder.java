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
            List<String> weatherColors = ColorMatcher.colorsByWeather(desc);
            List<String> validColors = ColorMatcher.matchTops(pants.getColor());

            List<Product> tops = filtered.stream()
                    .filter(p -> p.getCategory().toUpperCase().contains("CAMISETA"))
                    .filter(p -> p.getColor() != null &&
                            weatherColors.stream().anyMatch(w -> p.getColor().toLowerCase().contains(w.toLowerCase())))
                    .filter(p -> validColors.stream().anyMatch(c -> p.getColor().toLowerCase().contains(c.toLowerCase())))
                    .toList();
            top = randomPick(tops);
        }


        Product jacket = null;
        if (temp < 20) {
            List<Product> jackets = filtered.stream()
                    .filter(p -> p.getCategory().toUpperCase().contains("CHAQUETA") ||
                            p.getCategory().toUpperCase().contains("ABRIGO"))
                    .toList();
            jacket = randomPick(jackets);
        }


        StringBuilder sb = new StringBuilder();
        double total = 0;


        sb.append("<div class='outfit-grid'>");

        if (top != null) {
            sb.append(createProductCard("Prenda Superior", top));
            total += top.getPrice();
        }

        if (pants != null) {
            sb.append(createProductCard("Pantalón", pants));
            total += pants.getPrice();
        }

        if (jacket != null) {
            sb.append(createProductCard("Complemento abrigo", jacket));
            total += jacket.getPrice();
        }

        sb.append("</div>");


        sb.append("<div style='margin-top: 30px; text-align: center; border-top: 2px solid #eee; padding-top: 20px;'>");
        sb.append("<span style='font-size: 1.5em; font-weight: 600;'>Precio total del outfit: ");
        sb.append("<span style='color: #e74c3c;'>").append(String.format("%.2f", total)).append(" €</span></span>");
        sb.append("</div>");

        return sb.toString();
    }


    private String createProductCard(String label, Product p) {
        return String.format("""
            <div class='card'>
                <div style='color: #888; font-size: 0.8em; margin-bottom: 5px; text-transform: uppercase;'>%s</div>
                <img src='%s' alt='%s'>
                <h3>%s</h3>
                <div style='color: #555; font-size: 0.9em; margin-bottom: 10px;'>Marca: %s | Color: %s</div>
                <div class='price'>%.2f €</div>
            </div>
            """,
                label,
                p.getImageUrl(),
                p.getName(),
                p.getName(),
                (p.getBrand() != null ? p.getBrand() : "Zalando"),
                (p.getColor() != null ? p.getColor() : "N/A"),
                p.getPrice());
    }
}