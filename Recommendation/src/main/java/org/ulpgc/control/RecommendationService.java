package org.ulpgc.control;


import org.ulpgc.control.persistence.DatamartStore;
import org.ulpgc.control.persistence.ProductRepository;
import org.ulpgc.control.persistence.WeatherRepository;
import org.ulpgc.model.Outfit;
import org.ulpgc.model.Product;

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