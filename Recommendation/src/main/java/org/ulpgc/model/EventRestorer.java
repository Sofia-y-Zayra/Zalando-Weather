package org.ulpgc.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.ulpgc.dacd.model.Product;
import org.ulpgc.persistence.ProductRepository;
import org.ulpgc.persistence.WeatherRepository;

public class EventRestorer {

    private final Gson gson = new Gson();

    private final WeatherRepository weatherRepo;
    private final ProductRepository productRepo;

    public EventRestorer(
            WeatherRepository weatherRepo,
            ProductRepository productRepo
    ) {
        this.weatherRepo = weatherRepo;
        this.productRepo = productRepo;
    }

    public void restore(String json, String topic) {

        JsonObject obj =
                gson.fromJson(json, JsonObject.class);

        String ts =
                obj.has("ts") ? obj.get("ts").getAsString() : "";

        if (topic.equalsIgnoreCase("Weather")) {

            restoreWeather(obj, ts);

        } else if (topic.equalsIgnoreCase("Product")) {

            restoreProduct(obj, ts);
        }
    }

    private void restoreWeather(JsonObject obj, String ts) {

        if (!obj.has("data")) return;

        JsonObject data =
                obj.getAsJsonObject("data");

        weatherRepo.save(
                data.get("city").getAsString(),
                data.get("temperature").getAsDouble(),
                data.get("description").getAsString(),
                ts
        );
    }

    private void restoreProduct(JsonObject obj, String ts) {

        if (!obj.has("payload")) return;

        JsonObject pObj =
                obj.getAsJsonObject("payload");

        Product p = new Product();

        p.setName(pObj.get("name").getAsString());
        p.setPrice(pObj.get("price").getAsDouble());
        p.setCategory(pObj.get("category").getAsString());

        p.setBrand(
                pObj.has("brand")
                        ? pObj.get("brand").getAsString()
                        : ""
        );

        p.setColor(
                pObj.has("color")
                        ? pObj.get("color").getAsString()
                        : ""
        );

        p.setImageUrl(
                pObj.has("imageUrl")
                        ? pObj.get("imageUrl").getAsString()
                        : ""
        );

        productRepo.save(p, ts);
    }
}
