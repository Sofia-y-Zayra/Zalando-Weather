package control;

import model.Weather;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class WeatherTransformer {

    public Weather transform(JSONObject data) {
        return new Weather(
                data.getString("city"),
                data.getString("datetime"),
                data.getDouble("temperature"),
                data.getInt("humidity"),
                data.getString("weather"),
                LocalDateTime.now().toString()
        );
    }
}