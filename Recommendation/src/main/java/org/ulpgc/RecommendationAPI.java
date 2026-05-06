package org.ulpgc;

import io.javalin.Javalin;
import org.ulpgc.model.DatamartStore;
import org.ulpgc.model.RecommendationService;
import org.ulpgc.model.WeatherRepository;

public class RecommendationAPI {

    private static final int PORT = 8080;
    private final RecommendationService recommendationService;
    private final WeatherRepository weatherRepo;

    public RecommendationAPI(DatamartStore datamart) {
        this.recommendationService = new RecommendationService(datamart);
        this.weatherRepo = new WeatherRepository(datamart);
    }

    public void start() {
        Javalin app = Javalin.create().start(PORT);


        app.get("/", ctx -> ctx.redirect("/recommend"));
        // ----------------------------------------

        app.get("/recommend", ctx -> {
            String city = ctx.queryParam("city");
            String gender = ctx.queryParam("gender");
            if (gender == null) gender = "MUJER";

            StringBuilder html = new StringBuilder();
            html.append("<html><head><meta charset='UTF-8'>");
            html.append("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap' rel='stylesheet'>");
            html.append("<style>");
            html.append("body { font-family: 'Poppins', sans-serif; background: #f8f9fa; margin: 0; padding: 20px; }");
            html.append(".container { max-width: 1000px; margin: auto; }");
            html.append(".header { text-align: center; padding: 40px; background: white; border-radius: 15px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); margin-bottom: 20px; }");
            html.append(".search-form { margin-top: 20px; }");
            html.append("input, select, button { padding: 12px; border: 1px solid #ddd; border-radius: 8px; font-size: 16px; margin: 5px; }");
            html.append("button { background: #000; color: white; cursor: pointer; transition: 0.3s; padding: 12px 25px; }");
            html.append("button:hover { background: #333; }");
            html.append(".weather-info { font-size: 1.2em; color: #555; margin-top: 15px; }");
            html.append(".outfit-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 25px; margin-top: 30px; }");
            html.append(".card { background: white; padding: 20px; border-radius: 15px; box-shadow: 0 4px 10px rgba(0,0,0,0.03); text-align: center; }");
            html.append(".card img { width: 100%; height: 350px; object-fit: cover; border-radius: 10px; margin-bottom: 15px; }");
            html.append(".card h3 { font-size: 1.1em; margin: 10px 0; color: #222; }");
            html.append(".price { color: #e74c3c; font-weight: 600; font-size: 1.2em; }");
            html.append("</style></head><body><div class='container'>");

            html.append("<div class='header'>");
            html.append("<h1>Outfit Planner AI</h1>");
            html.append("<form class='search-form' action='/recommend' method='get'>");
            html.append("<input type='text' name='city' placeholder='Ciudad (ej: Alicante)' value='").append(city != null ? city : "").append("' required>");
            html.append("<select name='gender'><option value='MUJER'>Mujer</option><option value='HOMBRE'>Hombre</option></select>");
            html.append("<button type='submit'>Ver Recomendación</button>");
            html.append("</form>");

            if (city != null) {
                double temp = weatherRepo.getTemperature(city);
                String desc = weatherRepo.getDescription(city);
                html.append("<div class='weather-info'>Clima en <b>").append(city).append("</b>: ").append(temp).append("°C, ").append(desc).append("</div>");
                html.append("</div>");

                String result = recommendationService.recommend(city, gender);
                html.append("<div class='outfit-grid'>").append(result).append("</div>");
            } else {
                html.append("<p style='text-align:center;'>Introduce una ciudad para comenzar</p></div>");
            }

            html.append("</div></body></html>");
            ctx.html(html.toString());
        });
    }
}