package org.ulpgc;

import io.javalin.Javalin;
import org.ulpgc.model.DatamartStore;
import org.ulpgc.model.RecommendationService;
import org.ulpgc.model.WeatherRepository;
import java.util.List;

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

        app.get("/recommend", ctx -> {
            String city = ctx.queryParam("city");
            String gender = ctx.queryParam("gender");
            String age = ctx.queryParam("age");

            if (gender == null) gender = "MUJER";
            if (age == null) age = "25";


            List<String> cities = List.of(
                    "Madrid", "Barcelona", "Valencia", "Sevilla", "Bilbao",
                    "Zaragoza", "Malaga", "Murcia", "Palma", "Alicante",
                    "Valladolid", "Vigo", "Gijon", "Oviedo", "Granada",
                    "Cordoba", "Santander", "Pamplona", "Salamanca"
            );

            StringBuilder html = new StringBuilder();
            html.append("<html><head><meta charset='UTF-8'>");
            html.append("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap' rel='stylesheet'>");

            html.append("<style>");
            html.append("body { font-family: 'Poppins', sans-serif; background: #f0f2f5; margin: 0; padding: 20px; color: #1a1a1a; }");
            html.append(".container { max-width: 1100px; margin: auto; }");
            html.append(".header { text-align: center; padding: 50px 20px; background: white; border-radius: 25px; box-shadow: 0 10px 30px rgba(0,0,0,0.05); margin-bottom: 30px; border: 1px solid #eee; }");
            html.append("h1 { font-weight: 600; font-size: 2.2em; letter-spacing: -1px; margin-bottom: 30px; color: #000; }");
            html.append(".search-form { display: flex; justify-content: center; gap: 15px; flex-wrap: wrap; align-items: flex-end; }");
            html.append(".input-group { display: flex; flex-direction: column; text-align: left; gap: 6px; }");
            html.append("label { font-size: 11px; font-weight: 600; text-transform: uppercase; color: #aaa; margin-left: 4px; }");
            html.append("select, input, button { padding: 12px 16px; border: 1.5px solid #efefef; border-radius: 12px; font-size: 14px; outline: none; transition: 0.3s; font-family: inherit; }");
            html.append("select:focus, input:focus { border-color: #000; }");
            html.append("button { background: #000; color: white; font-weight: 600; border: none; cursor: pointer; padding: 12px 30px; }");
            html.append("button:hover { background: #333; transform: translateY(-2px); }");
            html.append(".weather-card { background: #000; color: #fff; padding: 10px 25px; border-radius: 50px; display: inline-block; margin-top: 25px; font-size: 14px; font-weight: 300; }");
            html.append(".outfit-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 25px; margin-top: 20px; }");
            html.append("</style></head><body><div class='container'>");

            html.append("<div class='header'>");
            html.append("<h1>Zalando <span style='font-weight:300'>Weather Stylist</span></h1>");

            html.append("<form class='search-form' action='/recommend' method='get'>");


            html.append("<div class='input-group'><label>Ciudad</label><select name='city' required>");
            html.append("<option value='' disabled ").append(city == null ? "selected" : "").append(">Selecciona...</option>");
            for (String c : cities) {
                html.append("<option value='").append(c).append("' ").append(c.equalsIgnoreCase(city) ? "selected" : "").append(">").append(c).append("</option>");
            }
            html.append("</select></div>");


            html.append("<div class='input-group'><label>Género</label><select name='gender'>");
            html.append("<option value='MUJER' ").append(gender.equals("MUJER") ? "selected" : "").append(">Mujer</option>");
            html.append("<option value='HOMBRE' ").append(gender.equals("HOMBRE") ? "selected" : "").append(">Hombre</option>");
            html.append("</select></div>");

            html.append("<button type='submit'>Analizar Clima</button>");
            html.append("</form>");

            if (city != null) {
                double temp = weatherRepo.getTemperature(city);
                String desc = weatherRepo.getDescription(city);
                html.append("<div class='weather-card'>📍 ").append(city.toUpperCase()).append(" &nbsp;|&nbsp; ").append(temp).append("°C &nbsp;|&nbsp; ").append(desc.toUpperCase()).append("</div>");
                html.append("</div>");

                String result = recommendationService.recommend(city, gender);
                html.append("<div class='outfit-grid'>").append(result).append("</div>");
            } else {
                html.append("</div><p style='text-align:center; color:#888;'>Elige una ciudad para ver el outfit recomendado</p>");
            }

            html.append("</div></body></html>");
            ctx.html(html.toString());
        });
    }
}