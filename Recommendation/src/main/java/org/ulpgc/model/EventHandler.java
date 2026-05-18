package org.ulpgc.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.ulpgc.model.persistence.DatamartStore;
import org.ulpgc.model.persistence.ProductRepository;
import org.ulpgc.model.persistence.WeatherRepository;
import org.ulpgc.utils.ColorParser;

public class EventHandler {

    private final ProductRepository productRepo;

    private final WeatherRepository weatherRepo;

    public EventHandler(DatamartStore db) {

        this.productRepo =
                new ProductRepository(db);

        this.weatherRepo =
                new WeatherRepository(db);
    }

    public void handle(
            String json,
            String topic
    ) {

        try {

            JsonObject obj =
                    JsonParser
                            .parseString(json)
                            .getAsJsonObject();

            String ts =
                    obj.has("ts")
                            ? obj.get("ts").getAsString()
                            : "";

            if (topic.equalsIgnoreCase("Product")) {

                processProduct(obj, ts);

            } else if (topic.equalsIgnoreCase("Weather")) {

                processWeather(obj, ts);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void processProduct(
            JsonObject obj,
            String ts
    ) {

        if (!obj.has("payload")) {
            return;
        }

        JsonObject p =
                obj.getAsJsonObject("payload");

        Product product =
                new Product();

        product.setName(
                p.get("name").getAsString()
        );

        product.setPrice(
                p.get("price").getAsDouble()
        );

        product.setCategory(
                p.get("category").getAsString()
        );

        product.setBrand(
                p.has("brand")
                        ? p.get("brand").getAsString()
                        : ""
        );

        product.setImageUrl(
                p.has("imageUrl")
                        ? p.get("imageUrl").getAsString()
                        : ""
        );

        product.setProductUrl(
                p.has("productUrl")
                        ? p.get("productUrl").getAsString()
                        : ""
        );

        if (p.has("color")) {

            product.setColor(
                    ColorParser.parse(
                            p.get("color").getAsString()
                    )
            );
        }

        productRepo.save(product, ts);
    }

    private void processWeather(
            JsonObject obj,
            String ts
    ) {

        if (!obj.has("payload")) {
            return;
        }

        JsonObject data =
                obj.getAsJsonObject("payload");

        weatherRepo.save(
                data.get("city").getAsString(),
                data.get("temperature").getAsDouble(),
                data.get("description").getAsString(),
                ts
        );
    }
}