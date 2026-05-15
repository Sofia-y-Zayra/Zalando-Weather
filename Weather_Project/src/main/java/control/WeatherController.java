package control;

import model.Weather;
import model.WeatherEvent;
import model.WeatherTransformer;
import model.WheatherApiConsumer;
import org.json.JSONObject;

import java.time.Instant;
import java.util.List;

import java.util.UUID;

public class WeatherController {

    private WheatherApiConsumer consumer;

    private WeatherTransformer transformer;

    private EventPublisher publisher;

    public WeatherController(
            WheatherApiConsumer consumer,
            EventPublisher publisher
    ) {

        this.consumer = consumer;

        this.transformer =
                new WeatherTransformer();

        this.publisher = publisher;
    }

    public void run(List<String> cities) {

        for (String city : cities) {

            try {

                JSONObject raw =
                        consumer.fetchForecast(city);

                JSONObject normalized =
                        consumer.normalizeForecast(raw);

                if (normalized != null) {

                    Weather weather =
                            transformer.transform(
                                    normalized
                            );


                    WeatherEvent event =
                            new WeatherEvent(
                                    UUID.randomUUID().toString(),
                                    Instant.now().toString(),
                                    "weather-feeder-canarias",
                                    "WEATHER_FORECAST",
                                    weather
                            );


                    publisher.publish(
                            "Weather",
                            event
                    );

                } else {

                    System.out.println(
                            "Sin datos: " + city
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "Error con ciudad: "
                                + city
                                + " -> "
                                + e.getMessage()
                );
            }
        }
    }
}