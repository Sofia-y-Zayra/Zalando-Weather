package model;

import com.google.gson.Gson;

public class WeatherEvent {
    private final String ts;    // Timestamp UTC
    private final String ss;    // Identificador de la fuente (ej. "feeder-weather-1")
    private final Weather data; // Tus datos actuales

    public WeatherEvent(String ts, String ss, Weather data) {
        this.ts = ts;
        this.ss = ss;
        this.data = data;
    }

    // Método para convertir este objeto a JSON fácilmente
    public String toJson() {
        return new Gson().toJson(this);
    }
}