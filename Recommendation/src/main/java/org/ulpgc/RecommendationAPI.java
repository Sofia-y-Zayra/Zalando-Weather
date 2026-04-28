package org.ulpgc;

import io.javalin.Javalin;
import org.ulpgc.persistence.DatamartStore;

public class RecommendationAPI {
    private final DatamartStore datamart;

    public RecommendationAPI(DatamartStore datamart) {
        this.datamart = datamart;
    }

    public void start() {
        Javalin app = Javalin.create().start(8080);

        app.get("/weather/{city}", ctx -> {
            ctx.result(datamart.getWeatherFor(ctx.pathParam("city")));
        });

        app.get("/recommend", ctx -> {
            String city = ctx.queryParam("city");
            if (city == null || city.isEmpty()) {
                ctx.status(400).result("Debes indicar una ciudad: ?city=Nombre");
            } else {
                 ctx.result(datamart.getSmartRecommendation(city));
            }
        });

        System.out.println("API REST iniciada en http://localhost:8080");
    }
}