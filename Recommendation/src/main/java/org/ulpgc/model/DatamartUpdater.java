package org.ulpgc.model;

import com.google.gson.JsonObject;
import org.ulpgc.dacd.model.Product;
import org.ulpgc.persistence.DatamartStore;
import org.ulpgc.persistence.ProductRepository;
import org.ulpgc.persistence.WeatherRepository;

public class DatamartUpdater {

    private final WeatherRepository weatherRepo;
    private final ProductRepository productRepo;

    private final WeatherEventMapper weatherMapper =
            new WeatherEventMapper();

    private final ProductEventMapper productMapper =
            new ProductEventMapper();

    public DatamartUpdater(DatamartStore db) {

        this.weatherRepo =
                new WeatherRepository(db);

        this.productRepo =
                new ProductRepository(db);
    }

    public void update(JsonObject obj, String topic) {

        String ts =
                obj.has("ts")
                        ? obj.get("ts").getAsString()
                        : "";

        if (topic.equalsIgnoreCase("Weather")) {

            WeatherData weather =
                    weatherMapper.map(obj);

            if (weather == null) return;

            weatherRepo.save(
                    weather.getCity(),
                    weather.getTemperature(),
                    weather.getDescription(),
                    ts
            );

        } else if (topic.equalsIgnoreCase("Product")) {

            Product product =
                    productMapper.map(obj);

            if (product == null) return;

            productRepo.save(product, ts);
        }
    }
}