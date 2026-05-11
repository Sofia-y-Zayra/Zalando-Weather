package org.ulpgc.service;


import org.ulpgc.model.Outfit;
import org.ulpgc.model.Product;
import org.ulpgc.persistence.DatamartStore;
import org.ulpgc.persistence.ProductRepository;
import org.ulpgc.persistence.WeatherRepository;
import org.ulpgc.visual.OutfitHtmlRenderer;

import java.util.List;

public class RecommendationService {

    private final WeatherRepository weatherRepo;
    private final ProductRepository productRepo;

    private final OutfitBuilder outfitBuilder =
            new OutfitBuilder();

    private final OutfitHtmlRenderer renderer =
            new OutfitHtmlRenderer();

    public RecommendationService(DatamartStore db) {

        this.weatherRepo =
                new WeatherRepository(db);

        this.productRepo =
                new ProductRepository(db);
    }

    public String recommend(String city, String gender) {

        double temp =
                weatherRepo.getTemperature(city);

        String desc =
                weatherRepo.getDescription(city);

        List<Product> products =
                productRepo.getAll();

        Outfit outfit =
                outfitBuilder.build(
                        temp,
                        desc,
                        gender,
                        products
                );

        return renderer.render(outfit);
    }
}