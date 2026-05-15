package view;

import control.EventPublisher;
import control.WeatherController;
import model.WheatherApiConsumer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        String API_KEY =
                "2996953de5904bd3d6c74a3f049e2664";

        List<String> cities =
                loadCities();

        if (cities.isEmpty()) {

            System.out.println(
                    "No se encontraron ciudades en cities.txt"
            );

            return;
        }

        WheatherApiConsumer consumer =
                new WheatherApiConsumer(API_KEY);

        EventPublisher publisher =
                new EventPublisher();

        WeatherController controller =
                new WeatherController(
                        consumer,
                        publisher
                );

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);

        Runnable task = () -> {

            System.out.println(
                    "\n=== INICIANDO CAPTURA Y ENVÍO AL BROKER ("
                            + cities.size()
                            + " ciudades) ==="
            );

            controller.run(cities);
        };

        scheduler.scheduleAtFixedRate(
                task,
                0,
                1,
                TimeUnit.HOURS
        );
    }

    private static List<String> loadCities() {

        List<String> cities =
                new ArrayList<>();

        try {

            InputStream is =
                    Main.class
                            .getClassLoader()
                            .getResourceAsStream(
                                    "cities.txt"
                            );

            if (is == null)
                return cities;

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(is)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.trim().isEmpty()) {

                    cities.add(
                            line.trim()
                    );
                }
            }

            reader.close();

        } catch (Exception e) {

            System.err.println(
                    "Error al cargar ciudades: "
                            + e.getMessage()
            );
        }

        return cities;
    }
}