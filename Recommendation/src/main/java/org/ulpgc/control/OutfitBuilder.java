package org.ulpgc.control;

import org.ulpgc.model.ColorType;
import org.ulpgc.model.Outfit;
import org.ulpgc.model.Product;
import org.ulpgc.utils.WeatherParser;
import org.ulpgc.model.WeatherType;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OutfitBuilder {

    private Product randomPick(List<Product> list) {

        if (list.isEmpty()) {
            return null;
        }

        return list.get(
                ThreadLocalRandom.current()
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
                                .contains(gender.toUpperCase())
                )

                .toList();

        WeatherType weather =
                WeatherParser.parse(desc);

        List<ColorType> weatherColorTypes =
                ColorMatcher.colorsByWeather(weather);


        List<Product> weatherFiltered = filtered.stream()

                .filter(p ->
                        p.getColor() != null &&
                                weatherColorTypes.contains(
                                        p.getColor()
                                )
                )

                .toList();


        List<Product> pantsList =
                weatherFiltered.stream()

                        .filter(p ->
                                p.getCategory()
                                        .toUpperCase()
                                        .contains("PANTALON")
                        )

                        .toList();

        Product pants =
                randomPick(pantsList);

        Product top = null;

        if (pants != null) {

            List<ColorType> validTopColorTypes =
                    ColorMatcher.matchTops(
                            pants.getColor()
                    );

            List<Product> tops =
                    weatherFiltered.stream()
                            .filter(p ->
                                    p.getCategory()
                                            .toUpperCase()
                                            .contains("CAMISETA")
                            )

                            .filter(p ->
                                    validTopColorTypes.contains(
                                            p.getColor()
                                    )
                            )

                            .toList();
            if (tops.isEmpty()) {

                tops = weatherFiltered.stream()

                        .filter(p ->
                                p.getCategory()
                                        .toUpperCase()
                                        .contains("CAMISETA")
                        )

                        .toList();
            }

            top = randomPick(tops);
        }

        Product jacket = null;

        if (temp < 20) {

            List<Product> jackets =
                    weatherFiltered.stream()

                            .filter(p ->
                                    p.getCategory()
                                            .toUpperCase()
                                            .contains("CHAQUETA")
                            )

                            .toList();

            jacket = randomPick(jackets);
        }


        return new Outfit(
                top,
                pants,
                jacket
        );
    }
}