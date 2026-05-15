package org.ulpgc.view;

import org.ulpgc.model.Outfit;
import org.ulpgc.model.Product;

import java.util.List;


public class RecommendationPageBuilder {

    public String buildPage(
            String city,
            String gender,
            double temp,
            String desc,
            Outfit outfit
    ) {

        List<String> cities = List.of(
                "Madrid", "Barcelona", "Valencia", "Sevilla",
                "Bilbao", "Zaragoza", "Malaga", "Murcia",
                "Palma", "Alicante", "Valladolid", "Vigo",
                "Gijon", "Oviedo", "Granada", "Cordoba",
                "Santander", "Pamplona", "Salamanca"
        );

        StringBuilder html =
                new StringBuilder();

        html.append("<html><head><meta charset='UTF-8'>");

        html.append("<link href='https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap' rel='stylesheet'>");

        html.append("<style>");

        html.append("""
            body {
                font-family: 'Poppins', sans-serif;
                background: #f0f2f5;
                margin: 0;
                padding: 20px;
                color: #1a1a1a;
            }

            .container {
                max-width: 1100px;
                margin: auto;
            }

            .header {
                text-align: center;
                padding: 50px 20px;
                background: white;
                border-radius: 25px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.05);
                margin-bottom: 30px;
            }

            h1 {
                font-size: 2.2em;
            }

            .search-form {
                display: flex;
                justify-content: center;
                gap: 15px;
                flex-wrap: wrap;
                align-items: flex-end;
            }

            .input-group {
                display: flex;
                flex-direction: column;
                gap: 6px;
            }

            select, button {
                padding: 12px 16px;
                border-radius: 12px;
                font-size: 14px;
            }

            button {
                background: black;
                color: white;
                border: none;
                cursor: pointer;
            }

            .weather-card {
                background: black;
                color: white;
                padding: 10px 25px;
                border-radius: 50px;
                display: inline-block;
                margin-top: 25px;
            }

            .outfit-grid {
                display: grid;
                grid-template-columns:
                    repeat(auto-fit, minmax(300px, 1fr));
                gap: 25px;
                margin-top: 20px;
            }

            .card {
                background: white;
                border-radius: 20px;
                padding: 20px;
                text-align: center;
                box-shadow: 0 5px 15px rgba(0,0,0,0.05);
            }

            img {
                width: 100%;
                border-radius: 15px;
            }

            .price {
                color: #e74c3c;
                font-size: 1.2em;
                margin-top: 10px;
            }
        """);

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
                    .append(c.equalsIgnoreCase(city)
                            ? "selected"
                            : "")
                    .append(">")
                    .append(c)
                    .append("</option>");
        }

        html.append("</select></div>");

        html.append("<div class='input-group'>");

        html.append("<label>Género</label>");

        html.append("<select name='gender'>");

        html.append("<option value='MUJER' ")
                .append("MUJER".equals(gender)
                        ? "selected"
                        : "")
                .append(">Mujer</option>");

        html.append("<option value='HOMBRE' ")
                .append("HOMBRE".equals(gender)
                        ? "selected"
                        : "")
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

            appendOutfit(html, outfit);

            html.append("</div>");

        } else {

            html.append("</div>");

            html.append("""
                <p style='text-align:center;color:#888;'>
                    Selecciona una ciudad
                </p>
            """);
        }

        html.append("</div></body></html>");

        return html.toString();
    }

    private void appendOutfit(
            StringBuilder html,
            Outfit outfit
    ) {

        if (outfit == null) {
            return;
        }

        if (outfit.getTop() != null) {

            html.append(createCard(
                    "Prenda superior",
                    outfit.getTop()
            ));
        }

        if (outfit.getPants() != null) {

            html.append(createCard(
                    "Pantalón",
                    outfit.getPants()
            ));
        }

        if (outfit.getJacket() != null) {

            html.append(createCard(
                    "Abrigo",
                    outfit.getJacket()
            ));
        }

        html.append("""
            <div style='grid-column:1/-1;
                        text-align:center;
                        margin-top:20px;
                        font-size:1.4em;'>
        """);

        html.append("Total Outfit: <b style='color:#e74c3c;'>")
                .append(String.format(
                        "%.2f",
                        outfit.totalPrice()
                ))
                .append(" €</b></div>");
    }

    private String createCard(
            String label,
            Product p
    ) {

        return String.format("""
            <div class='card'>

                <div style='color:#888;
                            font-size:0.75em;
                            margin-bottom:5px;
                            text-transform:uppercase;'>

                    %s

                </div>

                <img src="data:image/jpeg;base64,%s">

                <h3>%s</h3>

                <div class='price'>%.2f €</div>

            </div>
            """,
                label,
                p.getImageUrl(),
                p.getName(),
                p.getPrice()
        );
    }
}
