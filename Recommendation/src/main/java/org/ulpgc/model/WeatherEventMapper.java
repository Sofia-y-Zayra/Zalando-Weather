package org.ulpgc.model;

import com.google.gson.JsonObject;

public class WeatherEventMapper {

    public WeatherData map(JsonObject obj) {

        if (!obj.has("data")) {
            return null;
        }

        JsonObject data =
                obj.getAsJsonObject("data");

        return new WeatherData(
                data.get("city").getAsString(),
                data.get("temperature").getAsDouble(),
                data.get("description").getAsString()
        );
    }
}
