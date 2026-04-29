package org.ulpgc.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DatamartUpdater {

    private static final String TOPIC_PRODUCT = "Product";
    private static final String TOPIC_WEATHER = "Weather";

    private final DatamartStore datamart;
    private final Gson gson = new Gson();

    public DatamartUpdater(DatamartStore datamart) {
        this.datamart = datamart;
    }

    public void update(String json, String topic) {

        try {
            JsonObject obj = gson.fromJson(json, JsonObject.class);

            String ts = obj.has("ts")
                    ? obj.get("ts").getAsString()
                    : "unknown";

            if (TOPIC_WEATHER.equals(topic)) {
                updateWeather(obj, ts);
            } else if (TOPIC_PRODUCT.equals(topic)) {
                updateProduct(obj, ts);
            }

        } catch (Exception e) {
            System.err.println(
                    "Error procesando JSON de "
                            + topic + ": "
                            + e.getMessage()
            );
        }
    }

    private void updateWeather(JsonObject obj, String ts) {

        if (!obj.has("data")) return;

        JsonObject data = obj.getAsJsonObject("data");

        datamart.updateWeather(
                data.get("city").getAsString(),
                data.get("temperature").getAsDouble(),
                data.get("description").getAsString(),
                ts
        );

        System.out.println(
                "[DATAMART] Clima actualizado: "
                        + data.get("city").getAsString()
        );
    }

    private void updateProduct(JsonObject obj, String ts) {

        if (!obj.has("product")) return;

        JsonObject product = obj.getAsJsonObject("product");

        datamart.upsertProduct(
                product.get("id").getAsString(),
                product.get("name").getAsString(),
                product.get("price").getAsDouble(),
                product.get("category").getAsString(),
                ts
        );

        System.out.println(
                "[DATAMART] Producto actualizado: "
                        + product.get("name").getAsString()
        );
    }
}
