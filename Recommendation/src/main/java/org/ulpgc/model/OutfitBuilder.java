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
                .filter(p -> p.getCategory().contains("PANTALON"))
                .toList();

        Product pants = randomPick(pantsList);


        Product top = null;

        if (pants != null) {

            List<String> weatherColors = ColorMatcher.colorsByWeather(desc);
            List<String> validColors = ColorMatcher.matchTops(pants.getColor());

            List<Product> tops = filtered.stream()
                    .filter(p -> p.getCategory().contains("CAMISETA"))
                    .filter(p -> p.getColor() != null &&
                            weatherColors.stream().anyMatch(w ->
                                    p.getColor().toLowerCase().contains(w)))
                    .filter(p -> validColors.stream().anyMatch(c ->
                            p.getColor().toLowerCase().contains(c)))
                    .toList();

            top = randomPick(tops);
        }



        Product jacket = null;

        if (temp < 20) {
            List<Product> jackets = filtered.stream()
                    .filter(p -> p.getCategory().contains("CHAQUETA"))
                    .toList();

            jacket = randomPick(jackets);
        }


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