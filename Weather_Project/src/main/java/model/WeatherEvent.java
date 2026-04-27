package model;

import com.google.gson.Gson;

public class WeatherEvent {
    private final String ts;
    private final String ss;
    private final Weather data;

    public WeatherEvent(String ts, String ss, Weather data) {
        this.ts = ts;
        this.ss = ss;
        this.data = data;
    }


    public String toJson() {
        return new Gson().toJson(this);
    }
}