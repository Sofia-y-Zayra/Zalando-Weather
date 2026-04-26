package model;

public class Weather {

    private String city;
    private String datetime;
    private double temperature;
    private int humidity;
    private String description;
    private String capturedAt;

    public Weather(String city, String datetime, double temperature, int humidity, String description, String capturedAt) {
        this.city = city;
        this.datetime = datetime;
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
        this.capturedAt = capturedAt;
    }

    public String getCity() { return city; }
    public String getDatetime() { return datetime; }
    public double getTemperature() { return temperature; }
    public int getHumidity() { return humidity; }
    public String getDescription() { return description; }
    public String getCapturedAt() { return capturedAt; }
}