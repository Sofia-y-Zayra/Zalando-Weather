package org.ulpgc.model;

import com.google.gson.JsonObject;
import org.ulpgc.dacd.model.Product;

public class ProductEventMapper {

    public Product map(JsonObject obj) {

        if (!obj.has("payload")) {
            return null;
        }

        JsonObject p =
                obj.getAsJsonObject("payload");

        Product product = new Product();

        product.setName(
                p.get("name").getAsString()
        );

        product.setPrice(
                p.get("price").getAsDouble()
        );

        product.setCategory(
                p.get("category").getAsString()
        );

        product.setBrand(
                p.has("brand")
                        ? p.get("brand").getAsString()
                        : ""
        );

        product.setColor(
                p.has("color")
                        ? p.get("color").getAsString()
                        : ""
        );

        product.setImageUrl(
                p.has("imageUrl")
                        ? p.get("imageUrl").getAsString()
                        : ""
        );

        return product;
    }
}
