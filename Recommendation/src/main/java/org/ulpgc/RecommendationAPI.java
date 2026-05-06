package org.ulpgc;

import io.javalin.Javalin;
import org.ulpgc.model.DatamartStore;
import org.ulpgc.model.RecommendationService;
import org.ulpgc.model.WeatherRepository;

public class RecommendationAPI {

    private static final int PORT = 8080;

    private final DatamartStore datamart;
    private final RecommendationService recommendationService;
    private final WeatherRepository weatherRepo;

    public RecommendationAPI(DatamartStore datamart) {
        this.datamart = datamart;
        this.recommendationService = new RecommendationService(datamart);
        this.weatherRepo = new WeatherRepository(datamart);
    }

    public void start() {

        Javalin app = Javalin.create(config -> {
            config.showJavalinBanner = false;
        }).start(PORT);

        registerRoutes(app);

        System.out.println("==================================");
        System.out.println("API REST iniciada correctamente");
        System.out.println("http://localhost:" + PORT);
        System.out.println("==================================");
    }

    private void registerRoutes(Javalin app) {

        app.get("/", ctx -> {
            ctx.result("""
                    API Recommendation activa.

                    Endpoints disponibles:

                    GET /weather/{city}
                    GET /recommend?city=Alicante
                    """);
        });

        app.get("/weather/{city}", ctx -> {

            String city = ctx.pathParam("city");

            double temp = weatherRepo.getTemperature(city);
            String desc = weatherRepo.getDescription(city);

            ctx.result("City: " + city +
                    " | Temp: " + temp +
                    "°C | Weather: " + desc);
        });

        app.get("/recommend", ctx -> {

            String city = ctx.queryParam("city");
            String gender = ctx.queryParam("gender");

            if (city == null || city.isBlank()) {
                ctx.status(400).result("Debes indicar ciudad");
                return;
            }

            if (gender == null || gender.isBlank()) {
                gender = "MUJER";
            }
            String recommendation = recommendationService.recommend(city, gender);

            ctx.contentType("text/html");
            ctx.result(recommendation);

        });
    }
}