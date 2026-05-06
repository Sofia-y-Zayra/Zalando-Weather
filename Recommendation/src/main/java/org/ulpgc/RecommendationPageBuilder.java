package org.ulpgc;

import java.util.List;

public class RecommendationPageBuilder {

    public String buildPage(
            String city,
            String gender,
            double temp,
            String desc,
            String outfitHtml
    ) {

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
        html.append("h1 { font-weight: 600; font-size: 2.2em; margin-bottom: 30px; }");
        html.append(".search-form { display: flex; justify-content: center; gap: 15px; flex-wrap: wrap; align-items: flex-end; }");
        html.append(".input-group { display: flex; flex-direction: column; text-align: left; gap: 6px; }");
        html.append("label { font-size: 11px; font-weight: 600; text-transform: uppercase; color: #aaa; }");
        html.append("select, button { padding: 12px 16px; border-radius: 12px; font-size: 14px; }");
        html.append("button { background: black; color: white; border: none; cursor: pointer; }");
        html.append(".weather-card { background: black; color: white; padding: 10px 25px; border-radius: 50px; display: inline-block; margin-top: 25px; }");
        html.append(".outfit-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 25px; margin-top: 20px; }");
        html.append("</style></head><body>");

        html.append("<div class='container'>");

        html.append("<div class='header'>");
        html.append("<h1>Zalando Weather Stylist</h1>");

        html.append("<form class='search-form' action='/recommend' method='get'>");


        html.append("<div class='input-group'>");
        html.append("<label>Ciudad</label>");
        html.append("<select name='city' required>");

        html.append("<option value='' disabled ")
                .append(city == null ? "selected" : "")
                .append(">Selecciona...</option>");

        for (String c : cities) {

            html.append("<option value='")
                    .append(c)
                    .append("' ")
                    .append(c.equalsIgnoreCase(city) ? "selected" : "")
                    .append(">")
                    .append(c)
                    .append("</option>");
        }

        html.append("</select></div>");


        html.append("<div class='input-group'>");
        html.append("<label>Género</label>");
        html.append("<select name='gender'>");

        html.append("<option value='MUJER' ")
                .append("MUJER".equals(gender) ? "selected" : "")
                .append(">Mujer</option>");

        html.append("<option value='HOMBRE' ")
                .append("HOMBRE".equals(gender) ? "selected" : "")
                .append(">Hombre</option>");

        html.append("</select></div>");

        html.append("<button type='submit'>Analizar clima</button>");
        html.append("</form>");

        if (city != null) {

            html.append("<div class='weather-card'>");
            html.append(city.toUpperCase())
                    .append(" | ")
                    .append(temp)
                    .append("°C | ")
                    .append(desc.toUpperCase());
            html.append("</div>");

            html.append("</div>");

            html.append("<div class='outfit-grid'>");
            html.append(outfitHtml);
            html.append("</div>");

        } else {

            html.append("</div>");
            html.append("<p style='text-align:center;color:#888;'>");
            html.append("Selecciona una ciudad");
            html.append("</p>");
        }

        html.append("</div></body></html>");

        return html.toString();
    }
}
