package org.ulpgc.model;

import org.ulpgc.model.utils.ColorMatcher;
import org.ulpgc.dacd.model.Product;
import java.util.List;

public class OutfitBuilder {

    private Product randomPick(List<Product> list) {

        if (list.isEmpty()) return null;

        return list.get(
                java.util.concurrent.ThreadLocalRandom
                        .current()
                        .nextInt(list.size())
        );
    }

    public Outfit build(
            double temp,
            String desc,
            String gender,
            List<Product> products
    ) {

        List<Product> filtered = products.stream()
                .filter(p ->
                        p.getCategory()
                                .toUpperCase()
                                .contains(gender.toUpperCase()))
                .toList();


        List<Product> pantsList = filtered.stream()
                .filter(p ->
                        p.getCategory()
                                .toUpperCase()
                                .contains("PANTALON"))
                .toList();

        Product pants = randomPick(pantsList);

        Product top = null;

        if (pants != null) {

            List<String> validColors =
                    ColorMatcher.matchTops(pants.getColor());

            List<Product> tops = filtered.stream()
                    .filter(p ->
                            p.getCategory()
                                    .toUpperCase()
                                    .contains("CAMISETA"))
                    .filter(p ->
                            p.getColor() != null &&
                                    validColors.stream().anyMatch(c ->
                                            p.getColor()
                                                    .toLowerCase()
                                                    .contains(c.toLowerCase())))
                    .toList();

            top = randomPick(tops);
        }

        Product jacket = null;

        if (temp < 20) {

            List<Product> jackets = filtered.stream()
                    .filter(p ->
                            p.getCategory()
                                    .toUpperCase()
                                    .contains("CHAQUETA"))
                    .toList();

            jacket = randomPick(jackets);
        }

        return new Outfit(top, pants, jacket);
    }
}