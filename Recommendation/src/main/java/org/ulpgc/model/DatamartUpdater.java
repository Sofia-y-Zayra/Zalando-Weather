package org.ulpgc.model;

import com.google.gson.JsonObject;
import org.ulpgc.dacd.model.Product;

public class DatamartUpdater {

    private final WeatherRepository weatherRepo;
    private final ProductRepository productRepo;

    public DatamartUpdater(DatamartStore db) {
        this.weatherRepo = new WeatherRepository(db);
        this.productRepo = new ProductRepository(db);
    }

    public void update(JsonObject obj, String topic) {

        String ts = obj.has("ts") ? obj.get("ts").getAsString() : "";

        if (topic.equalsIgnoreCase("Weather")) {
            updateWeather(obj, ts);
        } else if (topic.equalsIgnoreCase("Product")) {
            updateProduct(obj, ts);
        }
    }

    private void updateWeather(JsonObject obj, String ts) {

        if (!obj.has("data")) return;

        JsonObject data = obj.getAsJsonObject("data");

        weatherRepo.save(
                data.get("city").getAsString(),
                data.get("temperature").getAsDouble(),
                data.get("description").getAsString(),
                ts
        );
    }

    private void updateProduct(JsonObject obj, String ts) {

        if (!obj.has("payload")) return;

        JsonObject p = obj.getAsJsonObject("payload");

        Product product = new Product();

        product.setName(p.get("name").getAsString());
        product.setPrice(p.get("price").getAsDouble());
        product.setCategory(p.get("category").getAsString());

        product.setBrand(p.has("brand") ? p.get("brand").getAsString() : "");
        product.setColor(p.has("color") ? p.get("color").getAsString() : "");
        product.setImageUrl(p.has("imageUrl") ? p.get("imageUrl").getAsString() : "");

        productRepo.save(product, ts);
    }
}