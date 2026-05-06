package org.ulpgc.model;

public class WeatherData {

    private final String city;
    private final double temperature;
    private final String description;

    public WeatherData(
            String city,
            double temperature,
            String description
    ) {
        this.city = city;
        this.temperature = temperature;
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }
}
