package org.ulpgc.visual;

import org.ulpgc.dacd.model.Product;
import org.ulpgc.model.Outfit;

public class OutfitHtmlRenderer {

    public String render(Outfit outfit) {

        StringBuilder sb = new StringBuilder();

        if (outfit.getTop() != null) {
            sb.append(createCard(
                    "Prenda superior",
                    outfit.getTop()
            ));
        }

        if (outfit.getPants() != null) {
            sb.append(createCard(
                    "Pantalón",
                    outfit.getPants()
            ));
        }

        if (outfit.getJacket() != null) {
            sb.append(createCard(
                    "Abrigo",
                    outfit.getJacket()
            ));
        }

        sb.append("""
            <div style='grid-column:1/-1;
                        text-align:center;
                        margin-top:20px;
                        font-size:1.4em;'>
        """);

        sb.append("Total Outfit: <b style='color:#e74c3c;'>")
                .append(String.format("%.2f", outfit.totalPrice()))
                .append(" €</b></div>");

        return sb.toString();
    }

    private String createCard(String label, Product p) {

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
