package org.ulpgc.model;

import org.ulpgc.utils.WeatherParser;

import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

import java.util.Collections;


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


        List<Product> filtered =
                products.stream()

                        .filter(p ->
                                p.getCategory()
                                        .toUpperCase()
                                        .contains(
                                                gender.toUpperCase()
                                        )
                        )

                        .toList();

        WeatherType weather =
                WeatherParser.parse(desc);



        List<Product> climateFiltered;

        if (weather == WeatherType.DESCONOCIDO) {

            climateFiltered = filtered;

        } else {

            List<ColorType> weatherColors =
                    ColorMatcher.colorsByWeather(
                            weather
                    );

            climateFiltered =
                    filtered.stream()

                            .filter(p ->
                                    p.getColor() != null &&
                                            weatherColors.contains(
                                                    p.getColor()
                                            )
                            )

                            .toList();
        }


        List<Product> tops =
                climateFiltered.stream()

                        .filter(p ->
                                p.getCategory()
                                        .toUpperCase()
                                        .contains("CAMISETA")
                        )

                        .toList();

        if (tops.isEmpty()) {

            tops = filtered.stream()

                    .filter(p ->
                            p.getCategory()
                                    .toUpperCase()
                                    .contains("CAMISETA")
                    )

                    .toList();
        }

        Product top =
                randomPick(tops);



        List<Product> pantsList =
                filtered.stream()

                        .filter(p ->
                                p.getCategory()
                                        .toUpperCase()
                                        .contains("PANTALON")
                        )

                        .toList();

        Product pants;

        if (top != null) {

            List<ColorType> validPantColors =
                    ColorMatcher.matchBottoms(
                            top.getColor()
                    );

            List<Product> compatiblePants =
                    pantsList.stream()

                            .filter(p ->
                                    validPantColors.contains(
                                            p.getColor()
                                    )
                            )

                            .toList();

            if (!compatiblePants.isEmpty()) {

                pants =
                        randomPick(
                                compatiblePants
                        );

            } else {

                pants =
                        randomPick(pantsList);
            }

        } else {

            pants =
                    randomPick(pantsList);
        }


        Product jacket = null;

        if (temp < 20) {

            List<Product> jackets =
                    climateFiltered.stream()

                            .filter(p ->
                                    p.getCategory()
                                            .toUpperCase()
                                            .contains("CHAQUETA")
                            )

                            .toList();

            jacket =
                    randomPick(jackets);
        }

        return new Outfit(
                top,
                pants,
                jacket
        );
    }
}