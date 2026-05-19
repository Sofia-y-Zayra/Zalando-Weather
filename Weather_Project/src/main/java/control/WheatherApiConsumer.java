package control;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class WheatherApiConsumer {

    private String apiKey;

    public WheatherApiConsumer(String apiKey) {
        this.apiKey = apiKey;
    }

    public JSONObject fetchForecast(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

            String urlStr = "https://api.openweathermap.org/data/2.5/forecast?q=" + encodedCity +
                    "&appid=" + apiKey + "&units=metric&lang=es";

            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() != 200) return null;

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) content.append(line);

            in.close();
            con.disconnect();

            return new JSONObject(content.toString());

        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject normalizeForecast(JSONObject data) {
        if (data == null) return null;

        JSONArray list = data.getJSONArray("list");

        LocalDateTime target = LocalDateTime.now().plusDays(1);

        JSONObject bestMatch = null;
        long minDiff = Long.MAX_VALUE;

        for (int i = 0; i < list.length(); i++) {
            JSONObject item = list.getJSONObject(i);

            long dt = item.getLong("dt");
            LocalDateTime forecastTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(dt),
                    ZoneId.systemDefault()
            );

            if (forecastTime.toLocalDate().equals(target.toLocalDate())) {
                long diff = Math.abs(forecastTime.getHour() - target.getHour());

                if (diff < minDiff) {
                    minDiff = diff;
                    bestMatch = item;
                }
            }
        }

        if (bestMatch == null) return null;

        JSONObject result = new JSONObject();

        result.put("city", data.getJSONObject("city").getString("name"));

        long dt = bestMatch.getLong("dt");
        LocalDateTime datetime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(dt),
                ZoneId.systemDefault()
        );

        result.put("datetime", datetime.toString());
        result.put("temperature", bestMatch.getJSONObject("main").getDouble("temp"));
        result.put("humidity", bestMatch.getJSONObject("main").getInt("humidity"));
        result.put("weather", bestMatch.getJSONArray("weather")
                .getJSONObject(0)
                .getString("description"));

        return result;
    }
}