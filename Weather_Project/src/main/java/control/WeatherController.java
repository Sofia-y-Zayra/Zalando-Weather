package control;

import model.Weather;
import model.WeatherEvent;
import model.WeatherTransformer;
import model.WheatherApiConsumer;
import org.json.JSONObject;
import persistence.JmsPublisher; // Cambio: ahora usamos el Publisher

import java.time.Instant;
import java.util.List;

public class WeatherController {

    private WheatherApiConsumer consumer;
    private WeatherTransformer transformer;
    private JmsPublisher publisher; // Sustituye a DBHandler

    public WeatherController(WheatherApiConsumer consumer, JmsPublisher publisher) {
        this.consumer = consumer;
        this.transformer = new WeatherTransformer();
        this.publisher = publisher;
    }

    public void run(List<String> cities) {
        for (String city : cities) {
            try {
                JSONObject raw = consumer.fetchForecast(city);
                JSONObject normalized = consumer.normalizeForecast(raw);

                if (normalized != null) {
                    Weather weather = transformer.transform(normalized);


                    WeatherEvent event = new WeatherEvent(
                            Instant.now().toString(),
                            "weather-feeder-canarias",
                            weather
                    );


                    publisher.publish("Weather", event.toJson());

                } else {
                    System.out.println("Sin datos: " + city);
                }

            } catch (Exception e) {
                System.out.println("Error con ciudad: " + city + " -> " + e.getMessage());
            }
        }
    }
}