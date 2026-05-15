package org.ulpgc.model;


import org.ulpgc.model.persistence.DatamartStore;
import org.ulpgc.model.persistence.ProductRepository;
import org.ulpgc.model.persistence.WeatherRepository;

import java.util.List;

public class RecommendationService {

    private final WeatherRepository weatherRepo;

    private final ProductRepository productRepo;

    private final OutfitBuilder outfitBuilder =
            new OutfitBuilder();

    public RecommendationService(
            DatamartStore db
    ) {

        this.weatherRepo =
                new WeatherRepository(db);

        this.productRepo =
                new ProductRepository(db);
    }

    public Outfit recommend(
            String city,
            String gender
    ) {

        double temp =
                weatherRepo.getTemperature(city);

        String desc =
                weatherRepo.getDescription(city);

        List<Product> products =
                productRepo.getAll();

        return outfitBuilder.build(
                temp,
                desc,
                gender,
                products
        );
    }
}