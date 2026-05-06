package org.ulpgc;

import io.javalin.Javalin;
import org.ulpgc.persistence.DatamartStore;
import org.ulpgc.model.RecommendationService;
import org.ulpgc.persistence.WeatherRepository;


public class RecommendationAPI {

    private static final int PORT = 8080;

    private final RecommendationService recommendationService;
    private final WeatherRepository weatherRepo;
    private final RecommendationPageBuilder pageBuilder;

    public RecommendationAPI(DatamartStore datamart) {

        this.recommendationService =
                new RecommendationService(datamart);

        this.weatherRepo =
                new WeatherRepository(datamart);

        this.pageBuilder =
                new RecommendationPageBuilder();
    }

    public void start() {

        Javalin app = Javalin.create().start(PORT);

        app.get("/", ctx -> ctx.redirect("/recommend"));

        app.get("/recommend", ctx -> {

            String city = ctx.queryParam("city");
            String gender = ctx.queryParam("gender");

            if (gender == null) {
                gender = "MUJER";
            }

            double temp = 0;
            String desc = "";
            String outfitHtml = "";

            if (city != null) {

                temp = weatherRepo.getTemperature(city);
                desc = weatherRepo.getDescription(city);

                outfitHtml =
                        recommendationService.recommend(city, gender);
            }

            String html = pageBuilder.buildPage(
                    city,
                    gender,
                    temp,
                    desc,
                    outfitHtml
            );

            ctx.html(html);
        });
    }
}