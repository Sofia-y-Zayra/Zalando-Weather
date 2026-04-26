package org.ulpgc.dacd.control;

import org.openqa.selenium.*;
import org.ulpgc.dacd.model.Product;
import org.ulpgc.dacd.model.util.*;

public class ProductParser {

    private final CategoryUtils categoryUtils = new CategoryUtils();
    private final ColorUtils colorUtils = new ColorUtils();

    public Product find(WebElement item, String url) {

        try {
            String text = item.getText();
            String[] lines = text.split("\n");

            String brand = "";
            String name = "";
            double price = 0;

            for (String line : lines) {

                String lower = line.toLowerCase();

                if (lower.contains("patrocinado") ||
                        lower.contains("oferta") ||
                        lower.contains("heart")) continue;

                if (brand.isEmpty() && !line.contains("€")) {
                    brand = line;
                    continue;
                }

                if (!brand.isEmpty() && name.isEmpty() && !line.contains("€")) {
                    name = line;
                }

                if (line.contains("€")) {
                    try {
                        price = Double.parseDouble(
                                line.replace("€", "")
                                        .replace(",", ".")
                                        .trim()
                                        .split(" ")[0]
                        );
                    } catch (Exception ignored) {}
                }
            }

            if (brand.isEmpty() || name.isEmpty()) return null;

            if (!categoryUtils.isClothing(name)) return null;

            Color color = colorUtils.extractColor(name);
            Category category = categoryUtils.getCategory(url, name);

            String base64Image = "";
            try {
                String imgUrl = item.findElement(By.cssSelector("img")).getAttribute("src");
                base64Image = ImageUtils.imageToBase64(imgUrl);
            } catch (Exception ignored) {}

            Product p = new Product();
            p.setName(name);
            p.setBrand(brand);
            p.setPrice(price);
            p.setColor(color.name());
            p.setImageUrl(base64Image);
            p.setCategory(category.name());
            p.setDate(java.time.LocalDate.now().toString());

            return p;

        } catch (Exception e) {
            return null;
        }
    }
}

